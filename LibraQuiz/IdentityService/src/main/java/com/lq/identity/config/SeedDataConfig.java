package com.lq.identity.config;

import com.lq.identity.entity.Role;
import com.lq.identity.entity.User;
import com.lq.identity.repository.RoleRepository;
import com.lq.identity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SeedDataConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SeedDataConfig(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").description("Administrator Role").build()));
        Role librarianRole = roleRepository.findByName("LIBRARIAN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("LIBRARIAN").description("Librarian Role").build()));
        Role teacherRole = roleRepository.findByName("TEACHER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("TEACHER").description("Teacher Role").build()));
        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseGet(() -> roleRepository.save(Role.builder().name("STUDENT").description("Student Role").build()));

        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@libraquiz.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(adminRole))
                    .build();
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("librarian")) {
            User librarian = User.builder()
                    .username("librarian")
                    .email("librarian@libraquiz.com")
                    .password(passwordEncoder.encode("lib123"))
                    .roles(Set.of(librarianRole))
                    .build();
            userRepository.save(librarian);
        }

        if (!userRepository.existsByUsername("teacher")) {
            User teacher = User.builder()
                    .username("teacher")
                    .email("teacher@libraquiz.com")
                    .password(passwordEncoder.encode("teach123"))
                    .roles(Set.of(teacherRole))
                    .build();
            userRepository.save(teacher);
        }

        if (!userRepository.existsByUsername("student")) {
            User student = User.builder()
                    .username("student")
                    .email("student@libraquiz.com")
                    .password(passwordEncoder.encode("student123"))
                    .roles(Set.of(studentRole))
                    .build();
            userRepository.save(student);
        }
    }
}
