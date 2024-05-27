package com.trip.post.model.dto;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDetailResponseDto {
    private PostResponseDto postResponseDto;
    private List<PostCommentDto> postCommentDtos;
}
