package com.lq.ai.service.impl;

import com.lq.ai.dto.StudyPlanRequest;
import com.lq.ai.entity.AIRecommendation;
import com.lq.ai.repository.AIRecommendationRepository;
import com.lq.ai.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final AIRecommendationRepository recommendationRepository;

    public RecommendationServiceImpl(AIRecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public List<AIRecommendation> getRecommendationsForUser(Long userId) {
        List<AIRecommendation> existing = recommendationRepository.findByUserId(userId);
        if (existing.isEmpty()) {
            AIRecommendation r1 = AIRecommendation.builder()
                    .userId(userId)
                    .recommendationType("BOOK")
                    .itemReferenceId(1L)
                    .itemTitle("Effective Java by Joshua Bloch")
                    .reason("Recommended based on your recent Spring Boot course enrollment.")
                    .build();

            AIRecommendation r2 = AIRecommendation.builder()
                    .userId(userId)
                    .recommendationType("COURSE")
                    .itemReferenceId(1L)
                    .itemTitle("Mastering Microservices with Spring Boot & Cloud")
                    .reason("High match with your target learning roadmap.")
                    .build();

            existing = List.of(recommendationRepository.save(r1), recommendationRepository.save(r2));
        }
        return existing;
    }

    @Override
    public Map<String, Object> generateStudyPlan(StudyPlanRequest request) {
        Map<String, Object> plan = new HashMap<>();
        plan.put("subject", request.getGoalSubject());
        plan.put("hoursPerWeek", request.getHoursPerWeek() != null ? request.getHoursPerWeek() : 10);
        plan.put("schedule", List.of(
                Map.of("week", 1, "topic", "Fundamentals & Core Syntax", "recommendedHours", 3),
                Map.of("week", 2, "topic", "Object-Oriented & Structural Architecture", "recommendedHours", 3),
                Map.of("week", 3, "topic", "Spring Boot & REST API Integration", "recommendedHours", 4),
                Map.of("week", 4, "topic", "Practice Quizzes & Final Assessment", "recommendedHours", 4)
        ));
        return plan;
    }
}
