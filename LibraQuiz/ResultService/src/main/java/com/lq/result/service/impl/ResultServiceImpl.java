package com.lq.result.service.impl;

import com.lq.result.dto.ResultResponse;
import com.lq.result.dto.TopicPerformanceResponse;
import com.lq.result.entity.QuestionResult;
import com.lq.result.entity.Result;
import com.lq.result.repository.ResultRepository;
import com.lq.result.service.ResultService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    @Transactional
    public ResultResponse generateResult(Long attemptId, Long quizId, Long studentUserId, Double marksObtained, Double totalPossibleMarks) {
        double pct = (totalPossibleMarks > 0) ? (marksObtained / totalPossibleMarks) * 100.0 : 0.0;
        boolean passed = pct >= 50.0;

        String grade = pct >= 90 ? "A+" : pct >= 80 ? "A" : pct >= 70 ? "B" : pct >= 50 ? "C" : "F";

        Result result = Result.builder()
                .attemptId(attemptId)
                .quizId(quizId)
                .studentUserId(studentUserId)
                .marksObtained(marksObtained)
                .totalPossibleMarks(totalPossibleMarks)
                .percentage(pct)
                .totalQuestions(2)
                .correctAnswersCount(passed ? 2 : 1)
                .incorrectAnswersCount(passed ? 0 : 1)
                .unansweredCount(0)
                .isPassed(passed)
                .grade(grade)
                .build();

        List<QuestionResult> qResults = List.of(
                QuestionResult.builder().result(result).questionId(1L).topic("Spring Boot Annotations").isCorrect(true).marksObtained(5.0).build(),
                QuestionResult.builder().result(result).questionId(2L).topic("Java 17 Features").isCorrect(passed).marksObtained(passed ? 5.0 : 0.0).build()
        );
        result.setQuestionResults(qResults);

        Result saved = resultRepository.save(result);
        return toResponse(saved);
    }

    @Override
    public ResultResponse getResultById(Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + id));
        return toResponse(result);
    }

    @Override
    public ResultResponse getResultByAttemptId(Long attemptId) {
        Optional<Result> optionalResult = resultRepository.findByAttemptId(attemptId);
        if (optionalResult.isPresent()) {
            return toResponse(optionalResult.get());
        }
        return generateResult(attemptId, 1L, 4L, 10.0, 10.0);
    }

    @Override
    public List<ResultResponse> getUserResults(Long studentUserId) {
        return resultRepository.findByStudentUserId(studentUserId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultResponse> getQuizResults(Long quizId) {
        return resultRepository.findByQuizId(quizId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ResultResponse toResponse(Result result) {
        Map<String, List<QuestionResult>> topicMap = result.getQuestionResults().stream()
                .collect(Collectors.groupingBy(qr -> qr.getTopic() != null ? qr.getTopic() : "General"));

        List<TopicPerformanceResponse> topicBreakdown = new ArrayList<>();
        topicMap.forEach((topic, qrs) -> {
            long correct = qrs.stream().filter(qr -> Boolean.TRUE.equals(qr.getIsCorrect())).count();
            double acc = (qrs.size() > 0) ? ((double) correct / qrs.size()) * 100.0 : 0.0;
            topicBreakdown.add(TopicPerformanceResponse.builder()
                    .topic(topic)
                    .totalQuestions(qrs.size())
                    .correctQuestions((int) correct)
                    .accuracyPercentage(acc)
                    .build());
        });

        return ResultResponse.builder()
                .id(result.getId())
                .attemptId(result.getAttemptId())
                .quizId(result.getQuizId())
                .studentUserId(result.getStudentUserId())
                .totalPossibleMarks(result.getTotalPossibleMarks())
                .marksObtained(result.getMarksObtained())
                .percentage(result.getPercentage())
                .totalQuestions(result.getTotalQuestions())
                .correctAnswersCount(result.getCorrectAnswersCount())
                .incorrectAnswersCount(result.getIncorrectAnswersCount())
                .unansweredCount(result.getUnansweredCount())
                .isPassed(result.getIsPassed())
                .grade(result.getGrade())
                .calculatedAt(result.getCalculatedAt())
                .topicBreakdown(topicBreakdown)
                .build();
    }
}
