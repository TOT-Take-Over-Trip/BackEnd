package com.trip.post.model.dto;

import com.trip.post.model.PostDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDetailResponseDto {
    private PostDto postDto;
    private List<PostCommentDto> postCommentDtos;
}
