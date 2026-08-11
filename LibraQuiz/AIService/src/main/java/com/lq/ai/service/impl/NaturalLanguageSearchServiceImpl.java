package com.lq.ai.service.impl;

import com.lq.ai.client.BookServiceClient;
import com.lq.ai.dto.NaturalLanguageSearchRequest;
import com.lq.ai.dto.StructuredSearchCriteria;
import com.lq.ai.entity.AIQuery;
import com.lq.ai.repository.AIQueryRepository;
import com.lq.ai.service.NaturalLanguageSearchService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NaturalLanguageSearchServiceImpl implements NaturalLanguageSearchService {

    private final BookServiceClient bookServiceClient;
    private final AIQueryRepository aiQueryRepository;

    public NaturalLanguageSearchServiceImpl(BookServiceClient bookServiceClient, AIQueryRepository aiQueryRepository) {
        this.bookServiceClient = bookServiceClient;
        this.aiQueryRepository = aiQueryRepository;
    }

    @Override
    public StructuredSearchCriteria parseQuery(String prompt) {
        String lower = prompt.toLowerCase();
        String category = "Programming";
        String subcategory = "General";
        String difficulty = "Intermediate";

        if (lower.contains("java")) subcategory = "Java";
        else if (lower.contains("python")) subcategory = "Python";
        else if (lower.contains("javascript") || lower.contains("js")) subcategory = "JavaScript";

        if (lower.contains("beginner") || lower.contains("basic") || lower.contains("start")) difficulty = "Beginner";
        else if (lower.contains("advanced") || lower.contains("expert")) difficulty = "Advanced";

        return StructuredSearchCriteria.builder()
                .category(category)
                .subcategory(subcategory)
                .topic(lower.contains("spring") ? "Spring Boot" : subcategory)
                .difficulty(difficulty)
                .originalPrompt(prompt)
                .build();
    }

    @Override
    public Map<String, Object> searchBooksWithAI(NaturalLanguageSearchRequest request) {
        StructuredSearchCriteria criteria = parseQuery(request.getPrompt());

        List<Object> books;
        try {
            books = bookServiceClient.searchBooks(criteria.getSubcategory());
            if (books == null || books.isEmpty()) {
                books = bookServiceClient.getAllBooks();
            }
        } catch (Exception e) {
            books = List.of();
        }

        aiQueryRepository.save(AIQuery.builder()
                .userId(request.getUserId())
                .userPrompt(request.getPrompt())
                .structuredCriteriaJson(criteria.toString())
                .build());

        Map<String, Object> response = new HashMap<>();
        response.put("prompt", request.getPrompt());
        response.put("structuredCriteria", criteria);
        response.put("results", books);
        return response;
    }
}
