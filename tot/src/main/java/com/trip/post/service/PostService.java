package com.trip.post.service;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostCommentDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostsResponseDto;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    PostsResponseDto getPosts() throws IOException;

    PostDetailResponseDto getPostById(int postId);

    List<PostDto> getPostsByMemberId(int memberId);

    List<PostDto> getPostsByMemberComments(int memberId);

    List<PostDto> getPostsByMemberLike(int memberId);

    void insertPostComment(PostCommentDto postCommentDto);

    void createPost(PostDto postDto, MultipartFile thumbnail) throws IOException;

    void updatePost(int postId, PostDto postDto);

    void deletePost(int postId);

}
