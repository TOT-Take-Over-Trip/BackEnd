package com.trip.post.service;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostCommentDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostResponseDto;
import com.trip.post.model.dto.PostsResponseDto;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    PostsResponseDto getPosts(int memberId) throws IOException;

    PostDetailResponseDto getPostById(HashMap<String,Integer> map);

    List<PostResponseDto> getPostsByMemberId(int memberId) throws IOException;

    List<PostResponseDto> getPostsByMemberComments(int memberId) throws IOException;

    List<PostResponseDto> getPostsByMemberLike(int memberId) throws IOException;

    PostCommentDto insertPostComment(PostCommentDto postCommentDto);

    void addLike(HashMap<String,Integer> map);

    void cancelLike(HashMap<String,Integer> map);

    void createPost(PostDto postDto, MultipartFile thumbnail) throws IOException;

    void updatePost(PostDto postDto, MultipartFile thumbnail) throws IOException;

    void deletePost(int postId);

}
