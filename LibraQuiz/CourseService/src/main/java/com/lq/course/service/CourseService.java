package com.lq.course.service;

import com.lq.course.dto.CourseRequest;
import com.lq.course.dto.CourseResponse;
import com.lq.course.dto.LessonRequest;
import com.lq.course.dto.ModuleRequest;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);
    CourseResponse getCourseById(Long id);
    List<CourseResponse> getAllCourses();
    List<CourseResponse> getCoursesByCategory(Long categoryId);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);

    CourseResponse.ModuleResponse addModule(ModuleRequest request);
    CourseResponse.LessonResponse addLesson(LessonRequest request);
}
