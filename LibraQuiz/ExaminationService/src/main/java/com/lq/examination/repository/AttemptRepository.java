package com.lq.examination.repository;

import com.lq.examination.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByStudentUserId(Long studentUserId);
    List<Attempt> findByQuizIdAndStudentUserId(Long quizId, Long studentUserId);
    Optional<Attempt> findTopByQuizIdAndStudentUserIdOrderByAttemptNumberDesc(Long quizId, Long studentUserId);
}
