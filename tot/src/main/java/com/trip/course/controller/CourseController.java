package com.trip.course.controller;

import com.trip.course.model.CourseDto;
import com.trip.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // TODO: 구현해야 함
    @GetMapping
    public List<CourseDto> getCourses() {
        return courseService.selectAllCourses();
    }

    // TODO: 구현해야 함
    @PostMapping
    public void addCourse(@RequestBody CourseDto course) {
        courseService.insertCourse(course);
    }

    // TODO: 구현해야 함
    @GetMapping("/{courseId}")
    public CourseDto getCourse(@PathVariable(value = "courseId") Long courseId) {
        return courseService.selectCourseById(courseId);
    }


    // TODO: 구현해야 함
    @PutMapping("/{courseId}")
    public void updateCourse(@PathVariable(value = "courseId") Long courseId, @RequestBody CourseDto course) {
        courseService.modifyCourse(course);
    }

    // TODO: 구현해야 함
    // 이거 memberId도 필요한듯
    @PostMapping("/takeover/{courseId}")
    public void takeover(@PathVariable(value = "courseId") Long courseId) {
//        courseService.takeOverCourse();
    }

    // TODO: 구현해야 함
    @PatchMapping("/{courseId}")
    public void patchCourse(@PathVariable(value = "courseId") Long courseId, @RequestBody CourseDto course) {

    }
}
