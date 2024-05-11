package com.trip.post.controller;

import com.trip.post.model.PostDto;
import com.trip.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{postId}")
    public PostDto getPost(@PathVariable int postId) {
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
