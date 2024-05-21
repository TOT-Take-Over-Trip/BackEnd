package com.trip.course.model.mapper;

import com.trip.course.model.CourseDto;
import com.trip.course.model.dto.CoursePlaceDto;
import com.trip.course.model.dto.CourseResponseDto;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    // 전체 코스 조회
    List<CourseResponseDto> selectAllCourses(int memberId);

    // 특정 코스 조회
    CourseResponseDto selectCourseById(HashMap<String, Object> map);

    //코스에 속해있는 장소 전부 조회
    List<CoursePlaceDto> selectCoursePlaces(int courseId);

    //코스에 속해있는 장소 단일 조회
    CoursePlaceDto selectCoursePlaceById(int coursePlaceId);

    //특정 회원이 보유한 코스 전부 조회
    List<CourseResponseDto> getCourseByMemberId(int memberId);

    //조회수 증가
    void updateHit(int courseId);

    //좋아요 활성화
    void addLike(HashMap<String, Object> map);

    //좋아요 취소
    void cancelLike(HashMap<String, Object> map);

    //코스에 장소 추가
    void insertCoursePlace(HashMap<String, Object> map);

    // 코스 추가
    void insertCourse(CourseDto course);

    // 코스 수정
    void modifyCourse(CourseDto courseDto);

    // 코스 인수
    void takeOverCourse(HashMap<String, Object> map);

    // 코스 삭제 => 실제로 삭제하는게 아니라 상태값만 변경
    void deleteCourse(CourseDto course);

    void deleteCoursePlaceByCourseId(int courseId);

}
