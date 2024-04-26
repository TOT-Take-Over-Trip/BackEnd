package com.trip.course.controller;

import com.trip.course.model.CourseDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    // TODO: 구현해야 함
    @GetMapping
    public List<CourseDto> getCourses() {
        return new ArrayList<CourseDto>();
    }

    // TODO: 구현해야 함
    @PostMapping
    public void addCourse(@RequestBody CourseDto course) {

    }

    // TODO: 구현해야 함
    @GetMapping("/{courseId}")
    public CourseDto getCourse(@PathVariable(value = "courseId") Long courseId) {
        return new CourseDto();
    }


    // TODO: 구현해야 함
    @PutMapping("/{courseId}")
    public void updateCourse(@PathVariable(value = "courseId") Long courseId, @RequestBody CourseDto course) {

    }

    // TODO: 구현해야 함
    @PostMapping("/takeover/{courseId}")
    public void takeover(@PathVariable(value = "courseId") Long courseId) {

    }

    // TODO: 구현해야 함
    @PatchMapping("/{courseId}")
    public void patchCourse(@PathVariable(value = "courseId") Long courseId, @RequestBody CourseDto course) {

    }
}
