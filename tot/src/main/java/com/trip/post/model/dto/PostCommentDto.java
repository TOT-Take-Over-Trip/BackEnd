package com.trip.post.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentDto {
    private int postCommentId;
    private int postId;
    private int memberId;
    private String memberName;
    private String content;
    private String createdDate;
    private String updatedDate;
    private String status;
}
