package com.lq.ai.service;

import com.lq.ai.dto.NaturalLanguageSearchRequest;
import com.lq.ai.dto.StructuredSearchCriteria;

import java.util.Map;

public interface NaturalLanguageSearchService {
    StructuredSearchCriteria parseQuery(String prompt);
    Map<String, Object> searchBooksWithAI(NaturalLanguageSearchRequest request);
}
