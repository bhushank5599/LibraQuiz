package com.lq.librarytransaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "BOOK-SERVICE")
public interface BookServiceClient {

    @GetMapping("/api/books/copies/code/{copyCode}")
    Map<String, Object> getCopyByCode(@PathVariable("copyCode") String copyCode);

    @PatchMapping("/api/books/copies/{copyId}/status")
    Map<String, Object> updateCopyStatus(@PathVariable("copyId") Long copyId, @RequestParam("status") String status);
}
