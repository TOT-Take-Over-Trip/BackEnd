package com.trip.post.model.mapper;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostCommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    List<PostDto> selectAllPosts();

    PostDto selectPostById(int postId);

    List<PostCommentDto> selectPostCommentsById(int postId);

    void insertPost(PostDto postDto);

    void updatePost(int postId, PostDto postDto);

    void deletePostById(int postId);

}
