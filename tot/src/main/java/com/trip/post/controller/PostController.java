package com.trip.post.controller;

import com.trip.post.model.PostDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    // TODO: 구현해야 함
    @GetMapping
    public List<PostDto> getPosts() {
        return new ArrayList<PostDto>();
    }

    // TODO: 구현해야 함
    @GetMapping("/{postId}")
    public PostDto getPost(@PathVariable Long postId) {
        return new PostDto();
    }

    // TODO: 구현해야 함
    @PostMapping
    public void addPost(@RequestBody PostDto postDto) {

    }

    // TODO: 구현해야 함
    @PutMapping("/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {

    }

    // TODO: 구현해야 함
    public void deletePost(@PathVariable Long postId) {

    }
}
