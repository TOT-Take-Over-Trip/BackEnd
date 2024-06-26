package com.trip.post.controller;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostCommentDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostResponseDto;
import com.trip.post.model.dto.PostsResponseDto;
import com.trip.post.service.PostService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;

    /**
     * 전체 게시글 조회
     * @param memberId
     */
    @GetMapping
    public PostsResponseDto getPosts(@RequestParam("memberId") int memberId) {
        try {
            return postService.getPosts(memberId);
        } catch (IOException e) {
            e.printStackTrace();    //TODO: 예외처리
        }
        return null;
    }

    /**
     * 특정 게시글 조회
     * @param postId
     * @param memberId
     */
    @GetMapping("/{postId}")
    public PostDetailResponseDto getPost(@PathVariable int postId,
        @RequestParam("memberId") int memberId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("postId", postId);
        map.put("memberId", memberId);
        return postService.getPostById(map);
    }

    /**
     * 내가 작성한 글 조회
     * @param memberId
     */
    @GetMapping("/members")
    public List<PostResponseDto> getPostsByMemberId(@RequestParam("memberId") int memberId)
        throws IOException {
        return postService.getPostsByMemberId(memberId);
    }

    /**
     * 내가 댓글 단 글 조회
     * @param memberId
     */
    @GetMapping("/comments/members")
    public List<PostResponseDto> getPostsByMemberComments(@RequestParam("memberId") int memberId)
        throws IOException {
        return postService.getPostsByMemberComments(memberId);
    }

    /**
     * 내가 좋아요 누른 글 조회
     * @param memberId
     */
    @GetMapping("/like/members")
    public List<PostResponseDto> selectPostsByMemberLike(@RequestParam("memberId") int memberId)
        throws IOException {
        return postService.getPostsByMemberLike(memberId);
    }

    /**
     * 게시글 등록
     * @param postDto
     * @param thumbnail
     */
    @PostMapping(value = "/new", consumes = {"multipart/form-data"})
    public void addPost(@RequestPart("postDto") PostDto postDto,
        @RequestPart("thumbnail") MultipartFile thumbnail) {
        try {
            postService.createPost(postDto, thumbnail);
        } catch (IOException e) {
            //TODO: 파일 업로드 예외처리
            e.printStackTrace();
        }
    }

    /**
     * 게시글 댓글 등록
     * @param postCommentDto
     */
    @PostMapping("/comments/new")
    public PostCommentDto addComment(@RequestBody PostCommentDto postCommentDto){
        return postService.insertPostComment(postCommentDto);
    }

    /**
     * 게시글 좋아요 누르기
     * @param postId
     * @param memberId
     */
    @PostMapping("/{postId}/like")
    public void addLike(@PathVariable("postId") int postId, @RequestParam("memberId") int memberId){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("postId", postId);
        map.put("memberId", memberId);
        postService.addLike(map);
    }

    @PostMapping("/{postId}/unlike")
    public void cancelLike(@PathVariable("postId") int postId, @RequestParam("memberId") int memberId){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("postId", postId);
        map.put("memberId", memberId);
        postService.cancelLike(map);
    }

    @PutMapping(value = "/{postId}", consumes = {"multipart/form-data"})
    public void updatePost(@RequestPart("postDto") PostDto postDto,
        @RequestPart("thumbnail") MultipartFile thumbnail) {
        try {
            postService.updatePost(postDto, thumbnail);
        } catch (IOException e) {
            //TODO: 파일 업로드 예외처리
            e.printStackTrace();
        }
    }

    @PostMapping("/{postId}")
    public void deletePost(@PathVariable(value = "postId") int postId) {
        postService.deletePost(postId);
    }
}
