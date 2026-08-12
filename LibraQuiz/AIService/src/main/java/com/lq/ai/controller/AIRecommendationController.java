package com.lq.ai.controller;

import com.lq.ai.dto.StudyPlanRequest;
import com.lq.ai.entity.AIRecommendation;
import com.lq.ai.service.RecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIRecommendationController {

    private final RecommendationService recommendationService;

    public AIRecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<List<AIRecommendation>> getRecommendationsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recommendationService.getRecommendationsForUser(userId));
    }

    @PostMapping("/study-plan")
    public ResponseEntity<Map<String, Object>> generateStudyPlan(@Valid @RequestBody StudyPlanRequest request) {
        return ResponseEntity.ok(recommendationService.generateStudyPlan(request));
    }
}
