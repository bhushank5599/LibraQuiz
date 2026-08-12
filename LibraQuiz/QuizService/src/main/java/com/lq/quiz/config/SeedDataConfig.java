package com.lq.quiz.config;

import com.lq.quiz.dto.QuizRequest;
import com.lq.quiz.dto.QuizResponse;
import com.lq.quiz.repository.QuizRepository;
import com.lq.quiz.service.QuizService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedDataConfig implements CommandLineRunner {

    private final QuizRepository quizRepository;
    private final QuizService quizService;

    public SeedDataConfig(QuizRepository quizRepository, QuizService quizService) {
        this.quizRepository = quizRepository;
        this.quizService = quizService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (quizRepository.count() == 0) {
            QuizResponse q1 = quizService.createQuiz(QuizRequest.builder()
                    .title("Spring Boot & Microservices Core Quiz")
                    .description("Test your knowledge on Spring Boot REST controllers, Eureka Discovery, and dependency injection.")
                    .courseId(1L)
                    .categoryId(4L)
                    .createdByTeacherId(3L)
                    .durationMinutes(15)
                    .passingMarks(5.0)
                    .maxAttempts(3)
                    .questionIds(List.of(1L, 2L))
                    .build());

            quizService.publishQuiz(q1.getId());
        }
    }
}
