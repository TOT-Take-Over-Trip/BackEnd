package com.trip.post.service;

import com.trip.post.model.PostDto;
import com.trip.post.model.mapper.PostMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostMapper postMapper;

    @Override
    public List<PostDto> getPosts() {
        return postMapper.selectAllPosts();
    }

    @Override
    public PostDto getPostById(int postId) {
        return postMapper.selectPostById(postId);
    }

    @Override
    public void createPost(PostDto postDto) {
        postMapper.insertPost(postDto);
    }

    @Override
    public void updatePost(int postId, PostDto postDto) {
        postMapper.updatePost(postId,postDto);
    }

    @Override
    public void deletePost(int postId) {
        postMapper.deletePostById(postId);
    }
}
