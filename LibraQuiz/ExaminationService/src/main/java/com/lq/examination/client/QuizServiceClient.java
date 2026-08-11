package com.lq.examination.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "QUIZ-SERVICE")
public interface QuizServiceClient {

    @GetMapping("/api/quizzes/{id}")
    Map<String, Object> getQuizById(@PathVariable("id") Long id);
}
