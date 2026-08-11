package com.lq.result.repository;

import com.lq.result.entity.QuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionResultRepository extends JpaRepository<QuestionResult, Long> {
    List<QuestionResult> findByResultId(Long resultId);
}
