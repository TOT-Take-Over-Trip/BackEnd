package com.trip.post.controller;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostsResponseDto;
import com.trip.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;

    @GetMapping
    public PostsResponseDto getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{postId}")
    public PostDetailResponseDto getPost(@PathVariable int postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/new")
    public void addPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
    }

    @PutMapping("/{postId}")
    public void updatePost(@PathVariable int postId, @RequestBody PostDto postDto) {
        postService.updatePost(postId,postDto);
    }

    public void deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
    }
}
