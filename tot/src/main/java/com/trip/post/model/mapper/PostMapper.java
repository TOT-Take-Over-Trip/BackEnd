package com.trip.post.model.mapper;

import com.trip.post.model.PostDto;
import com.trip.post.model.dto.PostCommentDto;
import com.trip.post.model.dto.PostResponseDto;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    List<PostResponseDto> selectAllPosts(int memberId);

    PostResponseDto selectPostById(HashMap<String,Integer> map);

    List<PostCommentDto> selectPostCommentsById(int postId);

    List<PostResponseDto> selectPostsByMemberId(int memberId);

    List<PostResponseDto> selectPostsByMemberComments(int memberId);

    List<PostResponseDto> selectPostsByMemberLike(int memberId);

    void addLike(HashMap<String,Integer> map);

    void cancelLike(HashMap<String,Integer> map);

    void addLikeCount(int postId);

    void cancelLikeCount(int postId);

    void insertPostComment(PostCommentDto postCommentDto);

    PostCommentDto selectPostCommentByPostCommentId(int postCommentId);

    void insertPost(PostDto postDto);

    void updatePost(PostDto postDto);

    void deletePostById(int postId);

}
