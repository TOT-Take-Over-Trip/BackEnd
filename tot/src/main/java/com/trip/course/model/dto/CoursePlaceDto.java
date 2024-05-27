package com.trip.course.model.dto;

import com.trip.place.model.PlaceDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursePlaceDto {
    private int coursePlaceId;
    private int courseId;
    private int placeId;
    private PlaceDto place;
    private String content;
    private int sequence;
    private String createdDate;
    private String updatedDate;
    private String status;

    @Builder
    public CoursePlaceDto(int placeId, String content) {
        this.placeId = placeId;
        this.content = content;
    }
}
