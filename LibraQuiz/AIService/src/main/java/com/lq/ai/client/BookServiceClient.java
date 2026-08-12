package com.lq.ai.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "BOOK-SERVICE")
public interface BookServiceClient {

    @GetMapping("/api/books/search")
    List<Object> searchBooks(@RequestParam("q") String query);

    @GetMapping("/api/books")
    List<Object> getAllBooks();
}
