package com.trip.post.service;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostDetailResponseDto;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostMapper postMapper;

    //파일 저장 위치
    @Value("${file.dir}")
    private String fileDir;

    @Override
    public PostsResponseDto getPosts() throws IOException {
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        List<PostDto> posts = postMapper.selectAllPosts();  //전체 post 데이터
        Collections.sort(posts, new Comparator<PostDto>() {
            @Override
            public int compare(PostDto o1, PostDto o2) {
                return o2.getPostLikeCount() - o1.getPostLikeCount();
            }
        }); //좋아요 순으로 정렬

        //이미지 처리(DB에는 경로 저장 -> 랜더링 후 바이트 스트링으로 변환하여 리턴해주기)
        for(PostDto post : posts){
            if(post.getThumbnail()!=null) {
                String filePath = post.getThumbnail();
                byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
                String base64EncodedString = Base64.getEncoder().encodeToString(bytes); //인코딩
                post.setThumbnail(base64EncodedString); //thumbnail에 인코딩 정보 넣어주기
            }
        }

        List<PostDto> topRankPosts = new ArrayList<>(); //좋아요 많은 데이터
        int index = 0;
        for(PostDto post : posts){
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
            if(index>=10) {
                break;
            }
        }
        postsResponseDto.setPosts(posts);
        postsResponseDto.setTopRankPosts(topRankPosts);
        return postsResponseDto;
    }

    @Override
    public PostDetailResponseDto getPostById(int postId) {
        PostDetailResponseDto postDetail = new PostDetailResponseDto();
        postDetail.setPostDto(postMapper.selectPostById(postId));
        postDetail.setPostCommentDtos(postMapper.selectPostCommentsById(postId));
        return postDetail;
    }

    @Override
    public List<PostDto> getPostsByMemberId(int memberId) {
        return postMapper.selectPostsByMemberId(memberId);
    }

    @Override
    public List<PostDto> getPostsByMemberComments(int memberId) {
        return postMapper.selectPostsByMemberComments(memberId);
    }

    @Override
    public List<PostDto> getPostsByMemberLike(int memberId) {
        return postMapper.selectPostsByMemberLike(memberId);
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
    public void updatePost(int postId, PostDto postDto) {
        postMapper.updatePost(postId,postDto);
    }

    @Override
    public void deletePost(int postId) {
        postMapper.deletePostById(postId);
    }
}
