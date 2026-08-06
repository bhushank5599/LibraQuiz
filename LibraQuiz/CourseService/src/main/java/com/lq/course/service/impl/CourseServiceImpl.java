package com.lq.course.service.impl;

import com.lq.course.dto.CourseRequest;
import com.lq.course.dto.CourseResponse;
import com.lq.course.dto.LessonRequest;
import com.lq.course.dto.ModuleRequest;
import com.lq.course.entity.Course;
import com.lq.course.entity.Lesson;
import com.lq.course.entity.Module;
import com.lq.course.repository.CourseRepository;
import com.lq.course.repository.LessonRepository;
import com.lq.course.repository.ModuleRepository;
import com.lq.course.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    public CourseServiceImpl(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .categoryId(request.getCategoryId())
                .teacherUserId(request.getTeacherUserId())
                .level(request.getLevel() != null ? request.getLevel() : "BEGINNER")
                .thumbnailUrl(request.getThumbnailUrl())
                .build();

        Course saved = courseRepository.save(course);
        return toResponse(saved);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getCoursesByCategory(Long categoryId) {
        return courseRepository.findByCategoryId(categoryId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategoryId(request.getCategoryId());
        course.setLevel(request.getLevel());

        return toResponse(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CourseResponse.ModuleResponse addModule(ModuleRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + request.getCourseId()));

        Module module = Module.builder()
                .course(course)
                .title(request.getTitle())
                .description(request.getDescription())
                .sequenceOrder(request.getSequenceOrder() != null ? request.getSequenceOrder() : course.getModules().size() + 1)
                .build();

        Module saved = moduleRepository.save(module);
        return CourseResponse.ModuleResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .sequenceOrder(saved.getSequenceOrder())
                .build();
    }

    @Override
    @Transactional
    public CourseResponse.LessonResponse addLesson(LessonRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + request.getModuleId()));

        Lesson lesson = Lesson.builder()
                .module(module)
                .title(request.getTitle())
                .content(request.getContent())
                .videoUrl(request.getVideoUrl())
                .durationMinutes(request.getDurationMinutes())
                .sequenceOrder(request.getSequenceOrder() != null ? request.getSequenceOrder() : module.getLessons().size() + 1)
                .bookReferenceId(request.getBookReferenceId())
                .build();

        Lesson saved = lessonRepository.save(lesson);
        return CourseResponse.LessonResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .videoUrl(saved.getVideoUrl())
                .durationMinutes(saved.getDurationMinutes())
                .sequenceOrder(saved.getSequenceOrder())
                .bookReferenceId(saved.getBookReferenceId())
                .build();
    }

    private CourseResponse toResponse(Course course) {
        List<CourseResponse.ModuleResponse> moduleResponses = course.getModules().stream()
                .map(m -> {
                    List<CourseResponse.LessonResponse> lessonResponses = m.getLessons().stream()
                            .map(l -> CourseResponse.LessonResponse.builder()
                                    .id(l.getId())
                                    .title(l.getTitle())
                                    .content(l.getContent())
                                    .videoUrl(l.getVideoUrl())
                                    .durationMinutes(l.getDurationMinutes())
                                    .sequenceOrder(l.getSequenceOrder())
                                    .bookReferenceId(l.getBookReferenceId())
                                    .build())
                            .collect(Collectors.toList());

                    return CourseResponse.ModuleResponse.builder()
                            .id(m.getId())
                            .title(m.getTitle())
                            .description(m.getDescription())
                            .sequenceOrder(m.getSequenceOrder())
                            .lessons(lessonResponses)
                            .build();
                })
                .collect(Collectors.toList());

        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .categoryId(course.getCategoryId())
                .teacherUserId(course.getTeacherUserId())
                .level(course.getLevel())
                .thumbnailUrl(course.getThumbnailUrl())
                .createdAt(course.getCreatedAt())
                .modules(moduleResponses)
                .build();
    }
}
