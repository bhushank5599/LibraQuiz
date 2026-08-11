package com.lq.ai.service.impl;

import com.lq.ai.dto.AIQuestionRequest;
import com.lq.ai.dto.AIQuestionResponse;
import com.lq.ai.entity.AIGeneratedQuestion;
import com.lq.ai.repository.AIQuestionRepository;
import com.lq.ai.service.AIQuestionGeneratorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIQuestionGeneratorServiceImpl implements AIQuestionGeneratorService {

    private final AIQuestionRepository aiQuestionRepository;

    public AIQuestionGeneratorServiceImpl(AIQuestionRepository aiQuestionRepository) {
        this.aiQuestionRepository = aiQuestionRepository;
    }

    @Override
    public List<AIQuestionResponse> generateQuestions(AIQuestionRequest request) {
        int count = request.getCount() != null ? request.getCount() : 2;
        String topic = request.getTopic();
        String diff = request.getDifficulty() != null ? request.getDifficulty() : "MEDIUM";

        List<AIQuestionResponse> generated = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            String questionText = "AI-Generated Q" + i + ": What is a primary best practice when working with " + topic + "?";
            List<String> options = List.of(
                    "Option A: Always maintain clear separation of concerns",
                    "Option B: Write all code in a single file",
                    "Option C: Ignore exception handling",
                    "Option D: Avoid using interfaces"
            );
            String explanation = "Separation of concerns enhances maintainability and modularity in software design.";

            AIGeneratedQuestion entity = AIGeneratedQuestion.builder()
                    .teacherUserId(request.getTeacherUserId())
                    .topic(topic)
                    .difficulty(diff)
                    .questionText(questionText)
                    .optionsJson(options.toString())
                    .explanation(explanation)
                    .status(AIGeneratedQuestion.QuestionDraftStatus.DRAFT)
                    .build();

            AIGeneratedQuestion saved = aiQuestionRepository.save(entity);

            generated.add(AIQuestionResponse.builder()
                    .id(saved.getId())
                    .topic(topic)
                    .difficulty(diff)
                    .questionText(questionText)
                    .options(options)
                    .correctAnswer("Option A: Always maintain clear separation of concerns")
                    .explanation(explanation)
                    .status("DRAFT")
                    .build());
        }

        return generated;
    }

    @Override
    public List<AIQuestionResponse> getDraftQuestions(Long teacherUserId) {
        return aiQuestionRepository.findByTeacherUserId(teacherUserId).stream()
                .map(q -> AIQuestionResponse.builder()
                        .id(q.getId())
                        .topic(q.getTopic())
                        .difficulty(q.getDifficulty())
                        .questionText(q.getQuestionText())
                        .explanation(q.getExplanation())
                        .status(q.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }
}
