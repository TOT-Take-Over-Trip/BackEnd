package com.trip.post.model.mapper;

import com.trip.post.model.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    List<PostDto> selectAllPost();

    PostDto selectPostById(int id);

    void insertPost(PostDto post);

    void updatePost(PostDto post);

    void deletePostById(int id);

}
