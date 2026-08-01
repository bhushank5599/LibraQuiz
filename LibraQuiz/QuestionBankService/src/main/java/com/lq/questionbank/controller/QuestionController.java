package com.lq.questionbank.controller;

import com.lq.questionbank.dto.QuestionRequest;
import com.lq.questionbank.dto.QuestionResponse;
import com.lq.questionbank.entity.enums.DifficultyLevel;
import com.lq.questionbank.entity.enums.QuestionType;
import com.lq.questionbank.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody QuestionRequest request) {
        return new ResponseEntity<>(questionService.createQuestion(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) QuestionType type,
            @RequestParam(required = false) DifficultyLevel level) {
        if (categoryId != null || type != null || level != null) {
            return ResponseEntity.ok(questionService.filterQuestions(categoryId, type, level));
        }
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.updateQuestion(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
