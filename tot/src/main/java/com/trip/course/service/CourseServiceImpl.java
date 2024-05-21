package com.trip.course.service;


import com.trip.course.model.CourseDto;
import com.trip.course.model.dto.CoursePlaceDto;
import com.trip.course.model.dto.CourseResponseDto;
import com.trip.course.model.mapper.CourseMapper;
import com.trip.member.model.MemberDto;
import com.trip.member.model.mapper.MemberMapper;
import com.trip.place.model.mapper.PlaceMapper;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final MemberMapper memberMapper;
    private final PlaceMapper placeMapper;

    //모든 코스 조회
    @Override
    public List<CourseResponseDto> getAllCourses(int memberId) {
        List<CourseResponseDto> courses = courseMapper.selectAllCourses(memberId);
        for(CourseResponseDto course : courses){
            List<CoursePlaceDto> coursePlaces = getCoursePlaces(course.getCourseId());
            for(CoursePlaceDto coursePlaceDto : coursePlaces){
                coursePlaceDto.setPlace(placeMapper.selectById(coursePlaceDto.getPlaceId()));
            }
            course.setCoursePlaces(coursePlaces);
        }
        return courses;
    }

    //단일 코스 조회
    @Override
    public CourseResponseDto getCourseById(int courseId, int memberId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        CourseResponseDto course = courseMapper.selectCourseById(map);
        if(memberId!=course.getMemberId()) {
            courseMapper.updateHit(courseId); //내가 만든 코스가 아니면 조회수 증가
        }
        List<CoursePlaceDto> coursePlaces = getCoursePlaces(course.getCourseId());
        for(CoursePlaceDto coursePlaceDto : coursePlaces){
            coursePlaceDto.setPlace(placeMapper.selectById(coursePlaceDto.getPlaceId()));
        }
        Collections.sort(coursePlaces, new Comparator<CoursePlaceDto>() {
            @Override
            public int compare(CoursePlaceDto o1, CoursePlaceDto o2) {
                return o1.getSequence() - o2.getSequence();
            }
        });
        course.setCoursePlaces(coursePlaces);
        return course;
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

    @Override
    public void addLike(int courseId, int memberId) {
        HashMap<String, Object> map =new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        courseMapper.addLike(map);
    }

    @Override
    public void cancelLike(int courseId, int memberId) {
        HashMap<String, Object> map =new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        courseMapper.cancelLike(map);
    }

    //코스 세부정보 넣기
    @Override
    public void insertCoursePlace(int courseId, CoursePlaceDto coursePlace) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("placeId", coursePlace.getPlaceId());
        map.put("content", coursePlace.getContent());
        map.put("sequence", coursePlace.getSequence());
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
    public void modifyCourse(int courseId, CourseDto course) {
        courseMapper.deleteCoursePlaceByCourseId(courseId); //기존에 저장되어있는 coursePlace 전부 삭제
        course.setCourseId(courseId);
        for (CoursePlaceDto coursePlace : course.getCoursePlaces()) {
            insertCoursePlace(courseId, coursePlace);   //코스 장소들 넣어주기
        }
        courseMapper.modifyCourse(course);
    }

    //코스 인수
    @Override
    public void takeOverCourse(int courseId, int memberId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        CourseResponseDto course = getCourseById(courseId, memberId);
        MemberDto member = memberMapper.selectMemberById(memberId);
        //TODO: 코스 살 수 있는지 점검필요 -> course 가격과 member 포인트 비교)
        courseMapper.takeOverCourse(map);
    }

    //코스 삭제
    @Override
    public void deleteCourse(CourseDto course) {
        courseMapper.deleteCourse(course);
    }
}
