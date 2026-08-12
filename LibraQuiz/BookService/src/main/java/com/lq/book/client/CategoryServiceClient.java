package com.lq.book.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "CATEGORY-SERVICE")
public interface CategoryServiceClient {

    @GetMapping("/api/categories/{id}")
    Map<String, Object> getCategoryById(@PathVariable("id") Long id);
}
