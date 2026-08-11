package com.lq.book.controller;

import com.lq.book.dto.BookCopyRequest;
import com.lq.book.dto.BookCopyResponse;
import com.lq.book.entity.BookCopy;
import com.lq.book.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/copies")
public class BookCopyController {

    private final BookService bookService;

    public BookCopyController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookCopyResponse> addBookCopy(@Valid @RequestBody BookCopyRequest request) {
        return new ResponseEntity<>(bookService.addBookCopy(request), HttpStatus.CREATED);
    }

    @GetMapping("/code/{copyCode}")
    public ResponseEntity<BookCopyResponse> getCopyByCode(@PathVariable String copyCode) {
        return ResponseEntity.ok(bookService.getCopyByCode(copyCode));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookCopyResponse>> getCopiesByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.getCopiesByBookId(bookId));
    }

    @PatchMapping("/{copyId}/status")
    public ResponseEntity<BookCopyResponse> updateCopyStatus(@PathVariable Long copyId, @RequestParam BookCopy.CopyStatus status) {
        return ResponseEntity.ok(bookService.updateCopyStatus(copyId, status));
    }
}
