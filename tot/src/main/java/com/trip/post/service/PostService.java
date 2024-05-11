package com.trip.post.service;

import com.trip.post.model.PostDto;
import java.util.List;

public interface PostService {

    List<PostDto> getPosts();

    PostDto getPostById(int postId);

    void createPost(PostDto postDto);

    void updatePost(int postId, PostDto postDto);

    void deletePost(int postId);

}
