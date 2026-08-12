package com.lq.result.controller;

import com.lq.result.dto.ResultResponse;
import com.lq.result.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getResultById(@PathVariable Long id) {
        return ResponseEntity.ok(resultService.getResultById(id));
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<ResultResponse> getResultByAttemptId(@PathVariable Long attemptId) {
        return ResponseEntity.ok(resultService.getResultByAttemptId(attemptId));
    }

    @GetMapping("/user/{studentUserId}")
    public ResponseEntity<List<ResultResponse>> getUserResults(@PathVariable Long studentUserId) {
        return ResponseEntity.ok(resultService.getUserResults(studentUserId));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<ResultResponse>> getQuizResults(@PathVariable Long quizId) {
        return ResponseEntity.ok(resultService.getQuizResults(quizId));
    }
}
