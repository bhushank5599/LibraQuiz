package com.lq.examination.controller;

import com.lq.examination.dto.AttemptResponse;
import com.lq.examination.dto.StartExamRequest;
import com.lq.examination.dto.SubmitAnswerRequest;
import com.lq.examination.service.AttemptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final AttemptService attemptService;

    public ExamController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/start")
    public ResponseEntity<AttemptResponse> startExam(@Valid @RequestBody StartExamRequest request) {
        return new ResponseEntity<>(attemptService.startExam(request), HttpStatus.CREATED);
    }

    @PostMapping("/submit")
    public ResponseEntity<AttemptResponse> submitExam(@Valid @RequestBody SubmitAnswerRequest request) {
        return ResponseEntity.ok(attemptService.submitExam(request));
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<AttemptResponse> getAttemptById(@PathVariable Long attemptId) {
        return ResponseEntity.ok(attemptService.getAttemptById(attemptId));
    }

    @GetMapping("/user/{studentUserId}")
    public ResponseEntity<List<AttemptResponse>> getUserAttempts(@PathVariable Long studentUserId) {
        return ResponseEntity.ok(attemptService.getUserAttempts(studentUserId));
    }
}
