package com.lq.questionmanagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "QUESTION-BANK-SERVICE")
public interface QuestionBankServiceClient {

    @GetMapping("/api/questions/{id}")
    Map<String, Object> getQuestionById(@PathVariable("id") Long id);
}
