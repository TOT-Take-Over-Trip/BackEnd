package com.trip.course.service;

import com.trip.course.model.CourseDto;

import java.util.List;

public interface CourseService {

    // 전체 코스 조회
    List<CourseDto> selectAllCourses();

    // 특정 코스 조회
    CourseDto selectCourseById(Long id);

    // 코스 추가
    void insertCourse(CourseDto course);

    // 코스 수정
    void modifyCourse(CourseDto course);

    // 코스 인수
    void takeOverCourse(CourseDto course);

    // 코스 삭제 => 실제로 삭제하는게 아니라 상태값만 변경
    void deleteCourse(CourseDto course);
}
