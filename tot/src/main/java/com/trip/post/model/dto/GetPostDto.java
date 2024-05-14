package com.trip.post.model.dto;

import com.trip.post.model.PostDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetPostDto {
    List<PostDto> Posts = new ArrayList<>();
    List<PostDto> topRankPosts = new ArrayList<>();
}
