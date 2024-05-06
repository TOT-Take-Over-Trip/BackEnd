package com.trip.course.service;


import com.trip.course.model.CourseDto;
import com.trip.course.model.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> selectAllCourses() {
        return courseMapper.selectAllCourses();
    }

    @Override
    public CourseDto selectCourseById(Long id) {
        return courseMapper.selectCourseById(id);
    }

    @Override
    public void insertCourse(CourseDto course) {
        courseMapper.insertCourse(course);
    }

    @Override
    public void modifyCourse(CourseDto course) {
        courseMapper.modifyCourse(course);
    }

    @Override
    public void takeOverCourse(CourseDto course) {
        courseMapper.takeOverCourse(course);
    }

    @Override
    public void deleteCourse(CourseDto course) {
        courseMapper.deleteCourse(course);
    }
}
