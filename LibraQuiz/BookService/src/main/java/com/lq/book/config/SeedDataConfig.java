package com.lq.book.config;

import com.lq.book.dto.BookRequest;
import com.lq.book.repository.BookRepository;
import com.lq.book.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedDataConfig implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final BookService bookService;

    public SeedDataConfig(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            Object[][] seedBooks = {
                {"Effective Java", "978-0134685991", "Joshua Bloch", "Java Programming", 5, 2018},
                {"Spring Boot in Action", "978-1617292545", "Craig Walls", "Web Development", 3, 2016},
                {"Python Crash Course", "978-1593279288", "Eric Matthes", "Python", 6, 2019},
                {"Clean Code: A Handbook of Agile Software Craftsmanship", "978-0132350884", "Robert C. Martin", "Software Engineering", 4, 2008},
                {"Designing Data-Intensive Applications", "978-1449373320", "Martin Kleppmann", "Distributed Systems", 7, 2017},
                {"Introduction to Algorithms (CLRS)", "978-0262033848", "Thomas H. Cormen", "Algorithms & Data Structures", 8, 2009},
                {"System Design Interview – An Insider's Guide", "978-1736049105", "Alex Xu", "System Design", 5, 2020},
                {"Head First Design Patterns", "978-0596007126", "Eric Freeman", "Software Architecture", 4, 2004},
                {"Computer Networking: A Top-Down Approach", "978-0133594140", "James Kurose", "Networking", 6, 2017},
                {"Modern Operating Systems", "978-0133591620", "Andrew S. Tanenbaum", "Operating Systems", 3, 2014},
                {"Database System Concepts", "978-0078022159", "Abraham Silberschatz", "Database Systems", 5, 2019},
                {"Artificial Intelligence: A Modern Approach", "978-0134610993", "Stuart Russell", "Artificial Intelligence", 4, 2020},
                {"Hands-On Machine Learning with Scikit-Learn, Keras, and TensorFlow", "978-1492032649", "Aurélien Géron", "Machine Learning", 7, 2019},
                {"The Pragmatic Programmer: Your Journey to Mastery", "978-0135957059", "David Thomas", "Software Engineering", 3, 2019},
                {"DevOps Handbook", "978-1942788003", "Gene Kim", "DevOps & Cloud", 5, 2016},
                {"Site Reliability Engineering", "978-1491929124", "Niall Richard Murphy", "DevOps & Cloud", 4, 2016},
                {"Real-World Bug Hunting: A Field Guide to Web Hacking", "978-1593278618", "Peter Yaworski", "Cybersecurity", 6, 2019},
                {"C# in Depth", "978-1617294532", "Jon Skeet", ".NET Programming", 2, 2019},
                {"Pro React 16", "978-1484244500", "Adam Freeman", "Web Development", 4, 2019},
                {"Learning SQL: Generate, Manipulate, and Retrieve Data", "978-1492057611", "Alan Beaulieu", "Database Systems", 8, 2020}
            };

            for (Object[] b : seedBooks) {
                try {
                    bookService.createBook(BookRequest.builder()
                            .title((String) b[0])
                            .isbn((String) b[1])
                            .description((String) b[0] + " by " + b[2])
                            .categoryId(4L)
                            .authorName((String) b[2])
                            .publisherName("Academic Publishing")
                            .publicationYear((Integer) b[5])
                            .edition("Latest Edition")
                            .coverImageUrl("https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?auto=format&fit=crop&w=400&q=80")
                            .initialCopiesCount((Integer) b[4])
                            .build());
                } catch (Exception e) {}
            }
        }
    }
}
