package com.trip.post.service;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostCommentDto;
import com.trip.post.model.dto.PostDetailResponseDto;
import com.trip.post.model.dto.PostsResponseDto;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    PostsResponseDto getPosts(int memberId) throws IOException;

    PostDetailResponseDto getPostById(HashMap<String,Integer> map);

    List<PostDto> getPostsByMemberId(int memberId);

    List<PostDto> getPostsByMemberComments(int memberId);

    List<PostDto> getPostsByMemberLike(int memberId);

    void insertPostComment(PostCommentDto postCommentDto);

    void insertOrUpdateLike(HashMap<String,Integer> map);

    void createPost(PostDto postDto, MultipartFile thumbnail) throws IOException;

    void updatePost(int postId, PostDto postDto);

    void deletePost(int postId);

}
