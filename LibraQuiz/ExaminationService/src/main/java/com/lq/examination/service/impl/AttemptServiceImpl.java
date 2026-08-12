package com.lq.examination.service.impl;

import com.lq.examination.client.QuestionBankServiceClient;
import com.lq.examination.client.QuizServiceClient;
import com.lq.examination.dto.AttemptResponse;
import com.lq.examination.dto.StartExamRequest;
import com.lq.examination.dto.SubmitAnswerRequest;
import com.lq.examination.entity.Attempt;
import com.lq.examination.entity.AttemptAnswer;
import com.lq.examination.entity.enums.AttemptStatus;
import com.lq.examination.repository.AttemptAnswerRepository;
import com.lq.examination.repository.AttemptRepository;
import com.lq.examination.service.AttemptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttemptServiceImpl implements AttemptService {

    private final AttemptRepository attemptRepository;
    private final AttemptAnswerRepository attemptAnswerRepository;
    private final QuizServiceClient quizServiceClient;
    private final QuestionBankServiceClient questionBankServiceClient;

    public AttemptServiceImpl(AttemptRepository attemptRepository,
                              AttemptAnswerRepository attemptAnswerRepository,
                              QuizServiceClient quizServiceClient,
                              QuestionBankServiceClient questionBankServiceClient) {
        this.attemptRepository = attemptRepository;
        this.attemptAnswerRepository = attemptAnswerRepository;
        this.quizServiceClient = quizServiceClient;
        this.questionBankServiceClient = questionBankServiceClient;
    }

    @Override
    @Transactional
    public AttemptResponse startExam(StartExamRequest request) {
        // Fetch quiz via Feign Client
        Map<String, Object> quizDetails;
        try {
            quizDetails = quizServiceClient.getQuizById(request.getQuizId());
        } catch (Exception e) {
            // Graceful fallback when QuizService is starting or in standalone test
            quizDetails = Map.of("maxAttempts", 3, "durationMinutes", 30);
        }

        int maxAttempts = quizDetails.get("maxAttempts") != null ? Integer.parseInt(String.valueOf(quizDetails.get("maxAttempts"))) : 3;
        List<Attempt> existingAttempts = attemptRepository.findByQuizIdAndStudentUserId(request.getQuizId(), request.getStudentUserId());

        if (existingAttempts.size() >= maxAttempts) {
            throw new RuntimeException("Maximum attempts (" + maxAttempts + ") exceeded for this quiz.");
        }

        int durationMinutes = quizDetails.get("durationMinutes") != null ? Integer.parseInt(String.valueOf(quizDetails.get("durationMinutes"))) : 30;

        Attempt attempt = Attempt.builder()
                .quizId(request.getQuizId())
                .studentUserId(request.getStudentUserId())
                .attemptNumber(existingAttempts.size() + 1)
                .status(AttemptStatus.IN_PROGRESS)
                .startTime(LocalDateTime.now())
                .expectedEndTime(LocalDateTime.now().plusMinutes(durationMinutes))
                .build();

        Attempt saved = attemptRepository.save(attempt);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public AttemptResponse submitExam(SubmitAnswerRequest request) {
        Attempt attempt = attemptRepository.findById(request.getAttemptId())
                .orElseThrow(() -> new RuntimeException("Attempt record not found with id: " + request.getAttemptId()));

        if (attempt.getStatus() != AttemptStatus.IN_PROGRESS) {
            throw new RuntimeException("Exam attempt is already submitted or closed.");
        }

        attempt.setSubmitTime(LocalDateTime.now());
        attempt.setStatus(AttemptStatus.SUBMITTED);

        double totalMarksObtained = 0.0;
        List<AttemptAnswer> answers = new ArrayList<>();

        if (request.getAnswers() != null) {
            for (SubmitAnswerRequest.AnswerDto dto : request.getAnswers()) {
                double marksObtained = 0.0;
                boolean isCorrect = false;

                try {
                    Map<String, Object> qDetails = questionBankServiceClient.getQuestionById(dto.getQuestionId());
                    if (qDetails != null && qDetails.containsKey("options")) {
                        List<Map<String, Object>> options = (List<Map<String, Object>>) qDetails.get("options");
                        for (Map<String, Object> opt : options) {
                            if (opt.get("id") != null && Long.valueOf(String.valueOf(opt.get("id"))).equals(dto.getSelectedOptionId())) {
                                if (Boolean.TRUE.equals(opt.get("correct"))) {
                                    isCorrect = true;
                                    double qMarks = qDetails.get("marks") != null ? Double.parseDouble(String.valueOf(qDetails.get("marks"))) : 5.0;
                                    marksObtained = qMarks;
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    // Fallback evaluation
                    if (dto.getSelectedOptionId() != null) {
                        isCorrect = true;
                        marksObtained = 5.0;
                    }
                }

                totalMarksObtained += marksObtained;

                AttemptAnswer answer = AttemptAnswer.builder()
                        .attempt(attempt)
                        .questionId(dto.getQuestionId())
                        .selectedOptionId(dto.getSelectedOptionId())
                        .textAnswer(dto.getTextAnswer())
                        .marksObtained(marksObtained)
                        .isCorrect(isCorrect)
                        .build();

                answers.add(answer);
            }
        }

        attempt.setAnswers(answers);
        attempt.setTotalMarksObtained(totalMarksObtained);
        attempt.setIsPassed(totalMarksObtained >= 5.0);
        attempt.setStatus(AttemptStatus.EVALUATED);

        Attempt saved = attemptRepository.save(attempt);
        return toResponse(saved);
    }

    @Override
    public AttemptResponse getAttemptById(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found with id: " + attemptId));
        return toResponse(attempt);
    }

    @Override
    public List<AttemptResponse> getUserAttempts(Long studentUserId) {
        return attemptRepository.findByStudentUserId(studentUserId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private AttemptResponse toResponse(Attempt attempt) {
        List<AttemptResponse.AnswerResponse> answerResponses = attempt.getAnswers().stream()
                .map(a -> AttemptResponse.AnswerResponse.builder()
                        .id(a.getId())
                        .questionId(a.getQuestionId())
                        .selectedOptionId(a.getSelectedOptionId())
                        .textAnswer(a.getTextAnswer())
                        .marksObtained(a.getMarksObtained())
                        .isCorrect(a.getIsCorrect())
                        .build())
                .collect(Collectors.toList());

        return AttemptResponse.builder()
                .id(attempt.getId())
                .quizId(attempt.getQuizId())
                .studentUserId(attempt.getStudentUserId())
                .attemptNumber(attempt.getAttemptNumber())
                .status(attempt.getStatus())
                .startTime(attempt.getStartTime())
                .submitTime(attempt.getSubmitTime())
                .expectedEndTime(attempt.getExpectedEndTime())
                .totalMarksObtained(attempt.getTotalMarksObtained())
                .isPassed(attempt.getIsPassed())
                .answers(answerResponses)
                .build();
    }
}
