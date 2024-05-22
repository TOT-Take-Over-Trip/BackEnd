package com.trip.course.service;

import com.trip.course.model.CourseDto;

import com.trip.course.model.dto.CoursePlaceDto;
import com.trip.course.model.dto.CourseResponseDto;
import java.util.HashMap;
import java.util.List;

public interface CourseService {

    // 전체 코스 조회
    List<CourseResponseDto> getAllCourses(int memberId);

    // 특정 코스 조회
    CourseResponseDto getCourseById(int courseId, int memberId);

    //코스에 속해있는 장소 전부 조회
    List<CoursePlaceDto> getCoursePlaces(int courseId);

    //코스에 속해있는 장소 단일 조회
    CoursePlaceDto getCoursePlaceById(int coursePlaceId);

    //좋아요 활성화
    void addLike(int courseId, int memberId);

    //좋아요 취소
    void cancelLike(int courseId, int memberId);

    //코스에 장소 추가
    void insertCoursePlace(int courseId, CoursePlaceDto coursePlace);

    // 코스 추가
    void insertCourse(CourseDto course);

    // 코스 수정
    void modifyCourse(int courseId, CourseDto course);

    // 코스 인수
    void takeOverCourse(int courseId, int memberId);

    // 코스 삭제 => 실제로 삭제하는게 아니라 상태값만 변경
    void deleteCourse(CourseDto course);

    //특정 회원이 가지고 있는 코스 전부 조회
    List<CourseResponseDto> getCourseByMemberId(int memberId);
}
