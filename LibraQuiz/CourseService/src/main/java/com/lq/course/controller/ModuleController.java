package com.lq.course.controller;

import com.lq.course.dto.CourseResponse;
import com.lq.course.dto.ModuleRequest;
import com.lq.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/modules")
public class ModuleController {

    private final CourseService courseService;

    public ModuleController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseResponse.ModuleResponse> addModule(@Valid @RequestBody ModuleRequest request) {
        return new ResponseEntity<>(courseService.addModule(request), HttpStatus.CREATED);
    }
}
