package com.trip.post.service;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostCommentDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostResponseDto;
import com.trip.post.model.dto.PostsResponseDto;
import com.trip.post.model.mapper.PostMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.temporal.ChronoUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostMapper postMapper;

    //파일 저장 위치
    @Value("${file.dir}")
    private String fileDir;

    @Override
    public PostsResponseDto getPosts(int memberId) throws IOException {
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        List<PostResponseDto> posts = postMapper.selectAllPosts(memberId);  //전체 post 데이터
        Collections.sort(posts, new Comparator<PostResponseDto>() {
            @Override
            public int compare(PostResponseDto o1, PostResponseDto o2) {
                return o2.getPostLikeCount() - o1.getPostLikeCount();
            }
        }); //좋아요 순으로 정렬

        //이미지 처리(DB에는 경로 저장 -> 랜더링 후 바이트 스트링으로 변환하여 리턴해주기)
        for(PostResponseDto post : posts){
            encodingImage(post);
        }

        List<PostResponseDto> topRankPosts = new ArrayList<>(); //좋아요 많은 데이터
        int index = 0;
        for(PostResponseDto post : posts){
            //30일 이상 지난 데이터를 사용하지 않기 위한 로직
            String date = post.getUpdatedDate().split(" ")[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            LocalDate today = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(parsedDate, today);

            if(daysBetween<30){
                topRankPosts.add(post);
                index++;
            }
            //10개 이상 넘으면 탈출
            if(index>=5) {
                break;
            }
        }
        postsResponseDto.setPosts(posts);
        postsResponseDto.setTopRankPosts(topRankPosts);
        return postsResponseDto;
    }

    @Override
    public PostDetailResponseDto getPostById(HashMap<String,Integer> map) {
        PostDetailResponseDto postDetail = new PostDetailResponseDto();
        postDetail.setPostResponseDto(postMapper.selectPostById(map));
        postDetail.setPostCommentDtos(postMapper.selectPostCommentsById(map.get("postId")));
        return postDetail;
    }

    @Override
    public List<PostResponseDto> getPostsByMemberId(int memberId) throws IOException {
        List<PostResponseDto> posts = postMapper.selectPostsByMemberId(memberId);
        for(PostResponseDto post : posts){
            encodingImage(post);
        }
        return posts;
    }

    @Override
    public List<PostResponseDto> getPostsByMemberComments(int memberId) throws IOException {
        List<PostResponseDto> posts = postMapper.selectPostsByMemberComments(memberId);
        for(PostResponseDto post : posts){
            encodingImage(post);
        }
        return posts;
    }

    @Override
    public List<PostResponseDto> getPostsByMemberLike(int memberId) throws IOException {
        List<PostResponseDto> posts = postMapper.selectPostsByMemberLike(memberId);
        for(PostResponseDto post : posts){
            encodingImage(post);
        }
        return posts;
    }

    @Override
    public PostCommentDto insertPostComment(PostCommentDto postCommentDto) {
        postMapper.insertPostComment(postCommentDto); //댓글 생성(주의: 리턴 값은 생성된 id가 아닌 영향 끼친 컬럼 개수라 1 리턴)
        int postCommentId = postCommentDto.getPostCommentId(); //생성된 댓글의 id 값
        return postMapper.selectPostCommentByPostCommentId(postCommentId); //생성된 댓글 정보 리턴해줌
    }

    @Override
    public void addLike(HashMap<String, Integer> map) {
        postMapper.addLikeCount(map.get("postId"));
        postMapper.addLike(map);
    }

    @Override
    public void cancelLike(HashMap<String, Integer> map) {
        postMapper.cancelLikeCount(map.get("postId"));
        postMapper.cancelLike(map);
    }

    @Override
    public void createPost(PostDto postDto, MultipartFile thumbnail) throws IOException {
        //파일 업로드 로직
        String fullPath = "";
        if(thumbnail!=null){
            //확장자 추출
            int pos = thumbnail.getOriginalFilename().lastIndexOf(".");
            String ext = thumbnail.getOriginalFilename().substring(pos+1);

            String uuid = UUID.randomUUID().toString();
            fullPath = fileDir + uuid + "." + ext;
            thumbnail.transferTo(new File(fullPath));
            postDto.setThumbnail(fullPath);
        }
        postMapper.insertPost(postDto);
    }

    @Override
    public void updatePost(PostDto postDto, MultipartFile thumbnail) throws IOException {
        String fullPath = "";
        if(thumbnail!=null){
            //확장자 추출
            int pos = thumbnail.getOriginalFilename().lastIndexOf(".");
            String ext = thumbnail.getOriginalFilename().substring(pos+1);

            String uuid = UUID.randomUUID().toString();
            fullPath = fileDir + uuid + "." + ext;
            thumbnail.transferTo(new File(fullPath));
            postDto.setThumbnail(fullPath);
        }
        postMapper.updatePost(postDto);
    }

    @Override
    public void deletePost(int postId) {
        postMapper.deletePostById(postId);
    }


    private void encodingImage(PostResponseDto post) throws IOException {
        if(post.getThumbnail()!=null) {
            String filePath = post.getThumbnail();
            byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
            String base64EncodedString = Base64.getEncoder().encodeToString(bytes); //인코딩
            post.setThumbnail(base64EncodedString); //thumbnail에 인코딩 정보 넣어주기
        }
    }
}
