package com.trip.course.model;


import lombok.Builder;
import lombok.Getter;

@Getter
public class CourseDto {

    private Long courseId;
    private Long memberId;
    private String title;
    private String content;
    private Long hit;
    private String createdDate;
    private String updatedDate;
    private String status;


    public CourseDto() {
    }

    @Builder
    public CourseDto(Long courseId, Long memberId, String title, String content, Long hit, String createdDate, String updatedDate, String status) {
        this.courseId = courseId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status = status;
    }
}
