package com.lq.ai.repository;

import com.lq.ai.entity.AIQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIQueryRepository extends JpaRepository<AIQuery, Long> {
    List<AIQuery> findByUserId(Long userId);
}
