package com.lq.questionbank.config;

import com.lq.questionbank.dto.QuestionRequest;
import com.lq.questionbank.entity.Question;
import com.lq.questionbank.repository.QuestionRepository;
import com.lq.questionbank.service.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedDataConfig implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    public SeedDataConfig(QuestionRepository questionRepository, QuestionService questionService) {
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (questionRepository.count() == 0) {
            // Sample Single Choice Question
            questionService.createQuestion(QuestionRequest.builder()
                    .questionText("Which annotation is used to declare a REST controller in Spring Boot?")
                    .questionType(Question.QuestionType.SINGLE_CHOICE)
                    .difficultyLevel("EASY")
                    .marks(5.0)
                    .explanation("@RestController combines @Controller and @ResponseBody.")
                    .categoryId(4L)
                    .topic("Spring Boot Annotations")
                    .bookReferenceId(2L)
                    .options(List.of(
                            new QuestionRequest.OptionDto("@Controller", false),
                            new QuestionRequest.OptionDto("@RestController", true),
                            new QuestionRequest.OptionDto("@Service", false),
                            new QuestionRequest.OptionDto("@Component", false)
                    ))
                    .build());

            // Sample True/False Question
            questionService.createQuestion(QuestionRequest.builder()
                    .questionText("In Java 17, Sealed Classes permit restricting which sub-classes can extend them.")
                    .questionType(Question.QuestionType.TRUE_FALSE)
                    .difficultyLevel("MEDIUM")
                    .marks(5.0)
                    .explanation("Sealed classes provide fine-grained control over inheritance hierarchy.")
                    .categoryId(4L)
                    .topic("Java 17 Features")
                    .bookReferenceId(1L)
                    .options(List.of(
                            new QuestionRequest.OptionDto("True", true),
                            new QuestionRequest.OptionDto("False", false)
                    ))
                    .build());
        }
    }
}
