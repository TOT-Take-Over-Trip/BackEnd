package com.trip.course.model;


import com.trip.course.model.dto.CoursePlaceDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseDto {

    private int courseId;
    private int memberId;
    private String title;
    private String content;
    private int hit;
    private int courseLikeCount;
    List<CoursePlaceDto> coursePlaces;
    private String createdDate;
    private String updatedDate;
    private String status;

    @Builder
    public CourseDto(int memberId, String title, String content, int hit, int courseLikeCount,
        List<CoursePlaceDto> coursePlaces) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.courseLikeCount = courseLikeCount;
        this.coursePlaces = coursePlaces;
    }
}
