package com.trip.course.service;


import com.trip.course.model.CourseDto;
import com.trip.course.model.dto.CoursePlaceDto;
import com.trip.course.model.dto.CourseResponseDto;
import com.trip.course.model.mapper.CourseMapper;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    //모든 코스 조회
    @Override
    public List<CourseResponseDto> getAllCourses(int memberId) {
        return courseMapper.selectAllCourses(memberId);
    }

    //단일 코스 조회
    @Override
    public CourseResponseDto getCourseById(HashMap<String, Object> map) {
        return courseMapper.selectCourseById(map);
    }

    //어떤 코스의 세부정보(장소 + 내용) 조회
    @Override
    public List<CoursePlaceDto> getCoursePlaces(int courseId) {
        return courseMapper.selectCoursePlaces(courseId);
    }

    //코스의 세부정보 단일조회
    @Override
    public CoursePlaceDto getCoursePlaceById(int coursePlaceId) {
        return courseMapper.selectCoursePlaceById(coursePlaceId);
    }

    //코스 세부정보 넣기
    @Override
    public void insertCoursePlace(int courseId, CoursePlaceDto coursePlace) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("placeId", coursePlace.getPlaceId());
        map.put("content", coursePlace.getContent());
        courseMapper.insertCoursePlace(map);
    }

    //코스 등록
    @Override
    public void insertCourse(CourseDto course) {
        courseMapper.insertCourse(course); //코스 생성되고 키를 course에 저장
        int courseId = course.getCourseId();
        for (CoursePlaceDto coursePlace : course.getCoursePlaces()) {
            insertCoursePlace(courseId, coursePlace);   //코스 장소들 넣어주기
        }
    }

    //코스 수정
    @Override
    public void modifyCourse(CourseDto course) {
        courseMapper.modifyCourse(course);
    }

    //코스 인수
    @Override
    public void takeOverCourse(CourseDto course) {
        courseMapper.takeOverCourse(course);
    }

    //코스 삭제
    @Override
    public void deleteCourse(CourseDto course) {
        courseMapper.deleteCourse(course);
    }
}
