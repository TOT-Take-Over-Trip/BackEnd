package com.trip.post.controller;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostsResponseDto;
import com.trip.post.service.PostService;
import java.io.IOException;
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
    public PostsResponseDto getPosts() {
        try {
            return postService.getPosts();
        } catch (IOException e) {
            e.printStackTrace();    //TODO: 예외처리
        }
        return null;
    }

    @GetMapping("/{postId}")
    public PostDetailResponseDto getPost(@PathVariable int postId) {
        return postService.getPostById(postId);
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


    @PutMapping("/{postId}")
    public void updatePost(@PathVariable int postId, @RequestBody PostDto postDto) {
        postService.updatePost(postId,postDto);
    }

    public void deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
    }
}
