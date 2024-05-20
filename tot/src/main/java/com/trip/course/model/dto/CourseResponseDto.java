package com.trip.course.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponseDto {
    private int courseId;
    private int memberId;
    private String title;
    private String content;
    private int hit;
    private int courseLikeCount;
    private boolean isLike;
    List<CoursePlaceDto> coursePlaces;
    private String createdDate;
    private String updatedDate;
    private String status;


}
