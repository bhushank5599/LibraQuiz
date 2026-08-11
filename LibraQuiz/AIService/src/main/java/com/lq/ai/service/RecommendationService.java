package com.lq.ai.service;

import com.lq.ai.dto.StudyPlanRequest;
import com.lq.ai.entity.AIRecommendation;

import java.util.List;
import java.util.Map;

public interface RecommendationService {
    List<AIRecommendation> getRecommendationsForUser(Long userId);
    Map<String, Object> generateStudyPlan(StudyPlanRequest request);
}
