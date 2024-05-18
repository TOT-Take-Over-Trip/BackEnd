package com.trip.post.controller;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostCommentDto;
import com.trip.post.model.dto.PostDetailResponseDto;
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

    @GetMapping
    public PostsResponseDto getPosts(@RequestParam("memberId") int memberId) {
        try {
            System.out.println(postService.getPosts(memberId).getPosts().size());
            return postService.getPosts(memberId);
        } catch (IOException e) {
            e.printStackTrace();    //TODO: 예외처리
        }
        return null;
    }

    @GetMapping("/{postId}")
    public PostDetailResponseDto getPost(@PathVariable int postId,
        @RequestParam("memberId") int memberId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("postId", postId);
        map.put("memberId", memberId);
        return postService.getPostById(map);
    }

    @GetMapping("/members/{memberId}")
    public List<PostDto> getPostsByMemberId(@PathVariable int memberId) {
        return postService.getPostsByMemberId(memberId);
    }

    @GetMapping("/comments/members/{memberId}")
    public List<PostDto> getPostsByMemberComments(@PathVariable int memberId) {
        return postService.getPostsByMemberComments(memberId);
    }

    @GetMapping("/like/members/{memberId}")
    public List<PostDto> selectPostsByMemberLike(@PathVariable int memberId) {
        return postService.getPostsByMemberLike(memberId);
    }



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

    @PostMapping("/comments/new")
    public void addComment(@RequestBody PostCommentDto postCommentDto){
        System.out.println(postCommentDto.getPostId());
        System.out.println(postCommentDto.getMemberId());
        postService.insertPostComment(postCommentDto);
    }

    @PutMapping("/{postId}")
    public void updatePost(@PathVariable int postId, @RequestBody PostDto postDto) {
        postService.updatePost(postId,postDto);
    }

    public void deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
    }
}
