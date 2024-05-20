package com.trip.course.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursePlaceDto {
    private int coursePlaceId;
    private int placeId;
    private String content;
    private String createdDate;
    private String updatedDate;
    private String status;

    @Builder
    public CoursePlaceDto(int placeId, String content) {
        this.placeId = placeId;
        this.content = content;
    }
}
