package com.lq.book.service.impl;

import com.lq.book.client.CategoryServiceClient;
import com.lq.book.dto.BookCopyRequest;
import com.lq.book.dto.BookCopyResponse;
import com.lq.book.dto.BookRequest;
import com.lq.book.dto.BookResponse;
import com.lq.book.entity.Author;
import com.lq.book.entity.Book;
import com.lq.book.entity.BookCopy;
import com.lq.book.entity.Publisher;
import com.lq.book.repository.AuthorRepository;
import com.lq.book.repository.BookCopyRepository;
import com.lq.book.repository.BookRepository;
import com.lq.book.repository.PublisherRepository;
import com.lq.book.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryServiceClient categoryServiceClient;

    public BookServiceImpl(BookRepository bookRepository,
                           BookCopyRepository bookCopyRepository,
                           AuthorRepository authorRepository,
                           PublisherRepository publisherRepository,
                           CategoryServiceClient categoryServiceClient) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryServiceClient = categoryServiceClient;
    }

    @Override
    @Transactional
    public BookResponse createBook(BookRequest request) {
        Author author = null;
        if (request.getAuthorName() != null && !request.getAuthorName().isBlank()) {
            author = authorRepository.findByName(request.getAuthorName())
                    .orElseGet(() -> authorRepository.save(Author.builder().name(request.getAuthorName()).build()));
        }

        Publisher publisher = null;
        if (request.getPublisherName() != null && !request.getPublisherName().isBlank()) {
            publisher = publisherRepository.findByName(request.getPublisherName())
                    .orElseGet(() -> publisherRepository.save(Publisher.builder().name(request.getPublisherName()).build()));
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .description(request.getDescription())
                .categoryId(request.getCategoryId())
                .author(author)
                .publisher(publisher)
                .publicationYear(request.getPublicationYear())
                .edition(request.getEdition())
                .coverImageUrl(request.getCoverImageUrl())
                .build();

        Book savedBook = bookRepository.save(book);

        int count = request.getInitialCopiesCount() != null ? request.getInitialCopiesCount() : 1;
        for (int i = 1; i <= count; i++) {
            String copyCode = "COPY-" + savedBook.getIsbn().replace("-", "") + "-" + String.format("%03d", i);
            BookCopy copy = BookCopy.builder()
                    .book(savedBook)
                    .copyCode(copyCode)
                    .status(BookCopy.CopyStatus.AVAILABLE)
                    .rackLocation("Rack " + (i % 5 + 1))
                    .build();
            bookCopyRepository.save(copy);
        }

        return toResponse(bookRepository.findById(savedBook.getId()).orElse(savedBook));
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toResponse(book);
    }

    @Override
    public BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));
        return toResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> searchBooks(String query) {
        return bookRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setCategoryId(request.getCategoryId());

        Book updated = bookRepository.save(book);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookCopyResponse addBookCopy(BookCopyRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + request.getBookId()));

        BookCopy copy = BookCopy.builder()
                .book(book)
                .copyCode(request.getCopyCode())
                .status(request.getStatus() != null ? request.getStatus() : BookCopy.CopyStatus.AVAILABLE)
                .rackLocation(request.getRackLocation())
                .conditionNote(request.getConditionNote())
                .build();

        BookCopy saved = bookCopyRepository.save(copy);
        return toCopyResponse(saved);
    }

    @Override
    @Transactional
    public BookCopyResponse updateCopyStatus(Long copyId, BookCopy.CopyStatus status) {
        BookCopy copy = bookCopyRepository.findById(copyId)
                .orElseThrow(() -> new RuntimeException("Book Copy not found with id: " + copyId));
        copy.setStatus(status);
        return toCopyResponse(bookCopyRepository.save(copy));
    }

    @Override
    public BookCopyResponse getCopyByCode(String copyCode) {
        BookCopy copy = bookCopyRepository.findByCopyCode(copyCode)
                .orElseThrow(() -> new RuntimeException("Book copy not found with code: " + copyCode));
        return toCopyResponse(copy);
    }

    @Override
    public List<BookCopyResponse> getCopiesByBookId(Long bookId) {
        return bookCopyRepository.findByBookId(bookId).stream()
                .map(this::toCopyResponse)
                .collect(Collectors.toList());
    }

    private BookResponse toResponse(Book book) {
        String categoryName = "General";
        try {
            if (book.getCategoryId() != null) {
                Map<String, Object> catMap = categoryServiceClient.getCategoryById(book.getCategoryId());
                if (catMap != null && catMap.containsKey("name")) {
                    categoryName = String.valueOf(catMap.get("name"));
                }
            }
        } catch (Exception e) {
            categoryName = "Category #" + book.getCategoryId();
        }

        List<BookCopy> copies = bookCopyRepository.findByBookId(book.getId());
        long available = copies.stream().filter(c -> c.getStatus() == BookCopy.CopyStatus.AVAILABLE).count();

        List<BookCopyResponse> copyResponses = copies.stream().map(this::toCopyResponse).collect(Collectors.toList());

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .description(book.getDescription())
                .categoryId(book.getCategoryId())
                .categoryName(categoryName)
                .authorName(book.getAuthor() != null ? book.getAuthor().getName() : "Unknown")
                .publisherName(book.getPublisher() != null ? book.getPublisher().getName() : "Unknown")
                .publicationYear(book.getPublicationYear())
                .edition(book.getEdition())
                .coverImageUrl(book.getCoverImageUrl())
                .totalCopies(copies.size())
                .availableCopies((int) available)
                .copies(copyResponses)
                .build();
    }

    private BookCopyResponse toCopyResponse(BookCopy copy) {
        return BookCopyResponse.builder()
                .id(copy.getId())
                .bookId(copy.getBook() != null ? copy.getBook().getId() : null)
                .bookTitle(copy.getBook() != null ? copy.getBook().getTitle() : null)
                .copyCode(copy.getCopyCode())
                .status(copy.getStatus())
                .rackLocation(copy.getRackLocation())
                .conditionNote(copy.getConditionNote())
                .build();
    }
}
