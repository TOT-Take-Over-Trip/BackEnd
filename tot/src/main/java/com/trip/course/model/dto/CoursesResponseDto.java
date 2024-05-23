package com.trip.course.model.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursesResponseDto {
    List<CourseResponseDto> courses = new ArrayList<>();
    List<CourseResponseDto> topRankCourses = new ArrayList<>();
}
