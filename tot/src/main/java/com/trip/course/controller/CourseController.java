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
        return courseService.getCourseById(courseId, memberId);
    }

    @GetMapping("/mycourse")
    public List<CourseResponseDto> getCourseByMemberId(@RequestParam("memberId") int memberId){
        return courseService.getCourseByMemberId(memberId);
    }

    @PostMapping("/like/{courseId}")
    public void addLike(@PathVariable("courseId") int courseId, @RequestParam("memberId") int memberId){
        courseService.addLike(courseId,memberId);
    }

    @PostMapping("/unlike/{courseId}")
    public void cancelLike(@PathVariable("courseId") int courseId, @RequestParam("memberId") int memberId){
        courseService.cancelLike(courseId,memberId);
    }

    // TODO: 구현해야 함
    @PutMapping("/{courseId}")
    public void updateCourse(@PathVariable(value = "courseId") int courseId, @RequestBody CourseDto course) {
        courseService.modifyCourse(courseId, course);
    }

    // 코스 인수
    @PostMapping("/takeover/{courseId}")
    public void takeover(@PathVariable int courseId, @RequestParam("memberId") int memberId) {
        courseService.takeOverCourse(courseId, memberId);
    }

    // TODO: 구현해야 함
    @PatchMapping("/{courseId}")
    public void patchCourse(@PathVariable(value = "courseId") Long courseId, @RequestBody CourseDto course) {

    }
}
