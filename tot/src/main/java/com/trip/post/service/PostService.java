package com.trip.post.service;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostsResponseDto;

public interface PostService {

    PostsResponseDto getPosts();

    PostDetailResponseDto getPostById(int postId);

    void createPost(PostDto postDto);

    void updatePost(int postId, PostDto postDto);

    void deletePost(int postId);

}
