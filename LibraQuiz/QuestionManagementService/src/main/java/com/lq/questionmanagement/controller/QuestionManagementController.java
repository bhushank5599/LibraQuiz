package com.lq.questionmanagement.controller;

import com.lq.questionmanagement.dto.QuestionManagementResponse;
import com.lq.questionmanagement.dto.ReviewRequest;
import com.lq.questionmanagement.entity.enums.QuestionStatus;
import com.lq.questionmanagement.service.QuestionWorkflowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question-management")
public class QuestionManagementController {

    private final QuestionWorkflowService workflowService;

    public QuestionManagementController(QuestionWorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/submit/{questionId}")
    public ResponseEntity<QuestionManagementResponse> submitForReview(@PathVariable Long questionId, @RequestParam Long teacherUserId) {
        return ResponseEntity.ok(workflowService.submitForReview(questionId, teacherUserId));
    }

    @PostMapping("/review")
    public ResponseEntity<QuestionManagementResponse> reviewQuestion(@Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(workflowService.reviewQuestion(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<QuestionManagementResponse> updateStatus(@PathVariable Long id, @RequestParam QuestionStatus status) {
        return ResponseEntity.ok(workflowService.updateStatus(id, status));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<QuestionManagementResponse>> getPendingReviews() {
        return ResponseEntity.ok(workflowService.getPendingReviews());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<QuestionManagementResponse>> getQuestionsByStatus(@PathVariable QuestionStatus status) {
        return ResponseEntity.ok(workflowService.getQuestionsByStatus(status));
    }
}
