package com.lq.course.controller;

import com.lq.course.dto.CourseResponse;
import com.lq.course.dto.LessonRequest;
import com.lq.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/lessons")
public class LessonController {

    private final CourseService courseService;

    public LessonController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseResponse.LessonResponse> addLesson(@Valid @RequestBody LessonRequest request) {
        return new ResponseEntity<>(courseService.addLesson(request), HttpStatus.CREATED);
    }
}
