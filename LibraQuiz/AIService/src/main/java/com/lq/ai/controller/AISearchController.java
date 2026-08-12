package com.lq.ai.controller;

import com.lq.ai.dto.NaturalLanguageSearchRequest;
import com.lq.ai.service.NaturalLanguageSearchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai/search")
public class AISearchController {

    private final NaturalLanguageSearchService searchService;

    public AISearchController(NaturalLanguageSearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> searchWithAI(@Valid @RequestBody NaturalLanguageSearchRequest request) {
        return ResponseEntity.ok(searchService.searchBooksWithAI(request));
    }
}
