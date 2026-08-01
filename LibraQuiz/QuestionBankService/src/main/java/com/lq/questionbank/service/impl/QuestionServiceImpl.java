package com.lq.questionbank.service.impl;

import com.lq.questionbank.dto.QuestionRequest;
import com.lq.questionbank.dto.QuestionResponse;
import com.lq.questionbank.entity.Question;
import com.lq.questionbank.entity.QuestionOption;
import com.lq.questionbank.entity.enums.DifficultyLevel;
import com.lq.questionbank.entity.enums.QuestionType;
import com.lq.questionbank.repository.QuestionRepository;
import com.lq.questionbank.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public QuestionResponse createQuestion(QuestionRequest request) {
        Question question = Question.builder()
                .questionText(request.getQuestionText())
                .questionType(request.getQuestionType())
                .difficultyLevel(request.getDifficultyLevel())
                .marks(request.getMarks())
                .explanation(request.getExplanation())
                .categoryId(request.getCategoryId())
                .topic(request.getTopic())
                .bookReferenceId(request.getBookReferenceId())
                .build();

        if (request.getOptions() != null) {
            List<QuestionOption> options = request.getOptions().stream()
                    .map(opt -> QuestionOption.builder()
                            .question(question)
                            .optionText(opt.getOptionText())
                            .isCorrect(opt.isCorrect())
                            .build())
                    .collect(Collectors.toList());
            question.setOptions(options);
        }

        Question saved = questionRepository.save(question);
        return toResponse(saved);
    }

    @Override
    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
        return toResponse(question);
    }

    @Override
    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionResponse> filterQuestions(Long categoryId, QuestionType type, DifficultyLevel level) {
        return questionRepository.findAll().stream()
                .filter(q -> categoryId == null || categoryId.equals(q.getCategoryId()))
                .filter(q -> type == null || type.name().equalsIgnoreCase(q.getQuestionType().name()))
                .filter(q -> level == null || level.name().equalsIgnoreCase(q.getDifficultyLevel()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionResponse updateQuestion(Long id, QuestionRequest request) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        question.setQuestionText(request.getQuestionText());
        question.setQuestionType(request.getQuestionType());
        question.setDifficultyLevel(request.getDifficultyLevel());
        question.setMarks(request.getMarks());
        question.setExplanation(request.getExplanation());

        return toResponse(questionRepository.save(question));
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }

    private QuestionResponse toResponse(Question question) {
        List<QuestionResponse.OptionResponse> optionResponses = question.getOptions().stream()
                .map(opt -> QuestionResponse.OptionResponse.builder()
                        .id(opt.getId())
                        .optionText(opt.getOptionText())
                        .isCorrect(opt.isCorrect())
                        .build())
                .collect(Collectors.toList());

        return QuestionResponse.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType())
                .difficultyLevel(question.getDifficultyLevel())
                .marks(question.getMarks())
                .explanation(question.getExplanation())
                .categoryId(question.getCategoryId())
                .topic(question.getTopic())
                .bookReferenceId(question.getBookReferenceId())
                .options(optionResponses)
                .build();
    }
}
