package com.lq.questionmanagement.service.impl;

import com.lq.questionmanagement.client.QuestionBankServiceClient;
import com.lq.questionmanagement.dto.QuestionManagementResponse;
import com.lq.questionmanagement.dto.ReviewRequest;
import com.lq.questionmanagement.entity.QuestionManagement;
import com.lq.questionmanagement.entity.Review;
import com.lq.questionmanagement.entity.enums.QuestionStatus;
import com.lq.questionmanagement.entity.enums.ReviewDecision;
import com.lq.questionmanagement.repository.QuestionManagementRepository;
import com.lq.questionmanagement.repository.QuestionVersionRepository;
import com.lq.questionmanagement.repository.ReviewRepository;
import com.lq.questionmanagement.service.QuestionWorkflowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionWorkflowServiceImpl implements QuestionWorkflowService {

    private final QuestionManagementRepository questionManagementRepository;
    private final QuestionVersionRepository questionVersionRepository;
    private final ReviewRepository reviewRepository;
    private final QuestionBankServiceClient questionBankServiceClient;

    public QuestionWorkflowServiceImpl(QuestionManagementRepository questionManagementRepository,
                                       QuestionVersionRepository questionVersionRepository,
                                       ReviewRepository reviewRepository,
                                       QuestionBankServiceClient questionBankServiceClient) {
        this.questionManagementRepository = questionManagementRepository;
        this.questionVersionRepository = questionVersionRepository;
        this.reviewRepository = reviewRepository;
        this.questionBankServiceClient = questionBankServiceClient;
    }

    @Override
    @Transactional
    public QuestionManagementResponse submitForReview(Long questionId, Long teacherUserId) {
        QuestionManagement qm = questionManagementRepository.findByQuestionId(questionId)
                .orElseGet(() -> QuestionManagement.builder()
                        .questionId(questionId)
                        .authorTeacherId(teacherUserId)
                        .status(QuestionStatus.DRAFT)
                        .currentVersion(1)
                        .build());

        qm.setStatus(QuestionStatus.UNDER_REVIEW);
        QuestionManagement saved = questionManagementRepository.save(qm);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public QuestionManagementResponse reviewQuestion(ReviewRequest request) {
        QuestionManagement qm = questionManagementRepository.findById(request.getQuestionManagementId())
                .orElseThrow(() -> new RuntimeException("Question management entity not found with id: " + request.getQuestionManagementId()));

        Review review = Review.builder()
                .questionManagement(qm)
                .reviewerUserId(request.getReviewerUserId())
                .decision(request.getDecision())
                .comments(request.getComments())
                .build();

        reviewRepository.save(review);

        if (ReviewDecision.APPROVE.equals(request.getDecision())) {
            qm.setStatus(QuestionStatus.APPROVED);
        } else if (ReviewDecision.REJECT.equals(request.getDecision())) {
            qm.setStatus(QuestionStatus.DRAFT);
        }

        return toResponse(questionManagementRepository.save(qm));
    }

    @Override
    @Transactional
    public QuestionManagementResponse updateStatus(Long questionManagementId, QuestionStatus status) {
        QuestionManagement qm = questionManagementRepository.findById(questionManagementId)
                .orElseThrow(() -> new RuntimeException("Question management entity not found with id: " + questionManagementId));

        qm.setStatus(status);
        return toResponse(questionManagementRepository.save(qm));
    }

    @Override
    public List<QuestionManagementResponse> getPendingReviews() {
        return questionManagementRepository.findByStatus(QuestionStatus.UNDER_REVIEW).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionManagementResponse> getQuestionsByStatus(QuestionStatus status) {
        return questionManagementRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private QuestionManagementResponse toResponse(QuestionManagement qm) {
        return QuestionManagementResponse.builder()
                .id(qm.getId())
                .questionId(qm.getQuestionId())
                .authorTeacherId(qm.getAuthorTeacherId())
                .status(qm.getStatus())
                .currentVersion(qm.getCurrentVersion())
                .createdAt(qm.getCreatedAt())
                .updatedAt(qm.getUpdatedAt())
                .build();
    }
}
