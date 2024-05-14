package com.trip.post.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private int postId;
    private int memberId;
    private String title;
    private String content;
    private String thumbnail;
    private int postLikeCount;
    private String createdDate;
    private String updatedDate;
    private String status;

    @Builder
    public PostDto(int postId, int memberId, String title, String content) {
        this.postId = postId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }
}
