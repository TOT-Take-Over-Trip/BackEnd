package com.trip.post.model.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {
    private int postId;
    private int memberId;
    private String memberName;
    private String title;
    private String content;
    private String thumbnail;
    private int postLikeCount;
    private boolean isLike;
    private String createdDate;
    private String updatedDate;
    private String status;

    @Builder
    public PostResponseDto(int postId, int memberId, String title, String content) {
        this.postId = postId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }
}