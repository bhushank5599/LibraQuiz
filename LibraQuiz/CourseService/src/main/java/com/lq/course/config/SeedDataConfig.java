package com.lq.course.config;

import com.lq.course.dto.CourseRequest;
import com.lq.course.dto.CourseResponse;
import com.lq.course.dto.LessonRequest;
import com.lq.course.dto.ModuleRequest;
import com.lq.course.repository.CourseRepository;
import com.lq.course.service.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedDataConfig implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final CourseService courseService;

    public SeedDataConfig(CourseRepository courseRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (courseRepository.count() == 0) {
            CourseResponse course = courseService.createCourse(CourseRequest.builder()
                    .title("Mastering Microservices with Spring Boot & Cloud")
                    .description("Build scalable, enterprise-ready microservices using Spring Cloud, Eureka, API Gateway & JPA.")
                    .categoryId(4L) // Java
                    .teacherUserId(3L)
                    .level("INTERMEDIATE")
                    .thumbnailUrl("https://images.unsplash.com/photo-1517694712202-14dd9538aa97?auto=format&fit=crop&w=400&q=80")
                    .build());

            CourseResponse.ModuleResponse module1 = courseService.addModule(ModuleRequest.builder()
                    .courseId(course.getId())
                    .title("Module 1: Microservice Foundations & Service Discovery")
                    .description("Introduction to Eureka Server and API Gateway architecture.")
                    .sequenceOrder(1)
                    .build());

            courseService.addLesson(LessonRequest.builder()
                    .moduleId(module1.getId())
                    .title("Lesson 1.1: Setting up Netflix Eureka Server")
                    .content("In this lesson we register our services with Eureka Discovery Server.")
                    .durationMinutes(20)
                    .sequenceOrder(1)
                    .bookReferenceId(1L)
                    .build());
        }
    }
}
