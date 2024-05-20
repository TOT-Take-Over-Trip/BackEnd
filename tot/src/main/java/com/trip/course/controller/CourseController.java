package com.trip.course.controller;

import com.trip.course.model.CourseDto;
import com.trip.course.model.dto.CourseResponseDto;
import com.trip.course.service.CourseService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseResponseDto> getCourses(@RequestParam("memberId") int memberId) {
        return courseService.getAllCourses(memberId);
    }

    @PostMapping
    public void addCourse(@RequestBody CourseDto course) {
        courseService.insertCourse(course);
    }

    @GetMapping("/{courseId}")
    public CourseResponseDto getCourseById(@PathVariable int courseId, @RequestParam("memberId") int memberId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("memberId", memberId);
        return courseService.getCourseById(map);
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
