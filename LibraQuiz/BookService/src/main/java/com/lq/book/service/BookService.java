package com.lq.book.service;

import com.lq.book.dto.BookCopyRequest;
import com.lq.book.dto.BookCopyResponse;
import com.lq.book.dto.BookRequest;
import com.lq.book.dto.BookResponse;
import com.lq.book.entity.BookCopy;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest request);
    BookResponse getBookById(Long id);
    BookResponse getBookByIsbn(String isbn);
    List<BookResponse> getAllBooks();
    List<BookResponse> searchBooks(String query);
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);

    BookCopyResponse addBookCopy(BookCopyRequest request);
    BookCopyResponse updateCopyStatus(Long copyId, BookCopy.CopyStatus status);
    BookCopyResponse getCopyByCode(String copyCode);
    List<BookCopyResponse> getCopiesByBookId(Long bookId);
}
