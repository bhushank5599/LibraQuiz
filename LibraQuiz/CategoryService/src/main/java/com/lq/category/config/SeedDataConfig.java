package com.lq.category.config;

import com.lq.category.entity.Category;
import com.lq.category.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedDataConfig implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public SeedDataConfig(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category prog = categoryRepository.save(Category.builder().name("Programming").description("Computer Software Development & Coding").build());
            Category math = categoryRepository.save(Category.builder().name("Mathematics").description("Pure and Applied Mathematics").build());
            Category sci = categoryRepository.save(Category.builder().name("Science & Technology").description("Natural Sciences, Engineering & Robotics").build());

            categoryRepository.save(Category.builder().name("Java").description("Java SE/EE Development").parentCategory(prog).build());
            categoryRepository.save(Category.builder().name("Python").description("Python Programming & AI").parentCategory(prog).build());
            categoryRepository.save(Category.builder().name("JavaScript").description("Web & Node.js Development").parentCategory(prog).build());

            categoryRepository.save(Category.builder().name("Calculus").description("Differential & Integral Calculus").parentCategory(math).build());
            categoryRepository.save(Category.builder().name("Linear Algebra").description("Vectors & Matrices").parentCategory(math).build());
        }
    }
}
