package com.lq.librarytransaction.repository;

import com.lq.librarytransaction.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUserId(Long userId);
    List<BorrowRecord> findByUserIdAndStatus(Long userId, BorrowRecord.BorrowStatus status);
    Optional<BorrowRecord> findByCopyCodeAndStatus(String copyCode, BorrowRecord.BorrowStatus status);
    List<BorrowRecord> findByStatus(BorrowRecord.BorrowStatus status);
}
