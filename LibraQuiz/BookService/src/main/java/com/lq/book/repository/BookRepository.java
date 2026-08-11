package com.lq.book.repository;

import com.lq.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByCategoryId(Long categoryId);
    List<Book> findByTitleContainingIgnoreCase(String query);
}
