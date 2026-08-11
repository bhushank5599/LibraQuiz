package com.lq.ai.controller;

import com.lq.ai.dto.AIQuestionRequest;
import com.lq.ai.dto.AIQuestionResponse;
import com.lq.ai.service.AIQuestionGeneratorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/questions")
public class AIQuestionController {

    private final AIQuestionGeneratorService questionGeneratorService;

    public AIQuestionController(AIQuestionGeneratorService questionGeneratorService) {
        this.questionGeneratorService = questionGeneratorService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<AIQuestionResponse>> generateQuestions(@Valid @RequestBody AIQuestionRequest request) {
        return ResponseEntity.ok(questionGeneratorService.generateQuestions(request));
    }

    @GetMapping("/drafts/{teacherUserId}")
    public ResponseEntity<List<AIQuestionResponse>> getDraftQuestions(@PathVariable Long teacherUserId) {
        return ResponseEntity.ok(questionGeneratorService.getDraftQuestions(teacherUserId));
    }
}
