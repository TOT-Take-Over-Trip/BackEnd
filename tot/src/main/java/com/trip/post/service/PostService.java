package com.trip.post.service;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostsResponseDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    PostsResponseDto getPosts() throws IOException;

    PostDetailResponseDto getPostById(int postId);

    void createPost(PostDto postDto, MultipartFile thumbnail) throws IOException;

    void updatePost(int postId, PostDto postDto);

    void deletePost(int postId);

}
