package com.lq.quiz.service.impl;

import com.lq.quiz.dto.QuizRequest;
import com.lq.quiz.dto.QuizResponse;
import com.lq.quiz.entity.Quiz;
import com.lq.quiz.entity.QuizQuestion;
import com.lq.quiz.repository.QuizQuestionRepository;
import com.lq.quiz.repository.QuizRepository;
import com.lq.quiz.service.QuizService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;

    public QuizServiceImpl(QuizRepository quizRepository, QuizQuestionRepository quizQuestionRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionRepository = quizQuestionRepository;
    }

    @Override
    @Transactional
    public QuizResponse createQuiz(QuizRequest request) {
        Quiz quiz = Quiz.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .courseId(request.getCourseId())
                .categoryId(request.getCategoryId())
                .createdByTeacherId(request.getCreatedByTeacherId())
                .durationMinutes(request.getDurationMinutes())
                .passingMarks(request.getPassingMarks() != null ? request.getPassingMarks() : 40.0)
                .negativeMarkingRate(request.getNegativeMarkingRate() != null ? request.getNegativeMarkingRate() : 0.0)
                .maxAttempts(request.getMaxAttempts() != null ? request.getMaxAttempts() : 3)
                .status(Quiz.QuizStatus.DRAFT)
                .build();

        Quiz savedQuiz = quizRepository.save(quiz);

        double totalMarks = 0.0;
        if (request.getQuestionIds() != null && !request.getQuestionIds().isEmpty()) {
            int seq = 1;
            for (Long qId : request.getQuestionIds()) {
                QuizQuestion qq = QuizQuestion.builder()
                        .quiz(savedQuiz)
                        .questionId(qId)
                        .sequenceOrder(seq++)
                        .weightageMarks(5.0)
                        .build();
                quizQuestionRepository.save(qq);
                totalMarks += 5.0;
            }
        }
        savedQuiz.setTotalMarks(totalMarks);

        return toResponse(quizRepository.save(savedQuiz));
    }

    @Override
    public QuizResponse getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        return toResponse(quiz);
    }

    @Override
    public List<QuizResponse> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizResponse> getPublishedQuizzes() {
        return quizRepository.findByStatus(Quiz.QuizStatus.PUBLISHED).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuizResponse publishQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        quiz.setStatus(Quiz.QuizStatus.PUBLISHED);
        return toResponse(quizRepository.save(quiz));
    }

    @Override
    @Transactional
    public QuizResponse updateQuiz(Long id, QuizRequest request) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setDurationMinutes(request.getDurationMinutes());

        return toResponse(quizRepository.save(quiz));
    }

    @Override
    @Transactional
    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new RuntimeException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }

    private QuizResponse toResponse(Quiz quiz) {
        List<Long> qIds = quiz.getQuizQuestions().stream()
                .map(QuizQuestion::getQuestionId)
                .collect(Collectors.toList());

        return QuizResponse.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .courseId(quiz.getCourseId())
                .categoryId(quiz.getCategoryId())
                .createdByTeacherId(quiz.getCreatedByTeacherId())
                .status(quiz.getStatus())
                .durationMinutes(quiz.getDurationMinutes())
                .totalMarks(quiz.getTotalMarks())
                .passingMarks(quiz.getPassingMarks())
                .negativeMarkingRate(quiz.getNegativeMarkingRate())
                .maxAttempts(quiz.getMaxAttempts())
                .createdAt(quiz.getCreatedAt())
                .questionIds(qIds)
                .build();
    }
}
