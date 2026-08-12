package com.lq.librarytransaction.service.impl;

import com.lq.librarytransaction.client.BookServiceClient;
import com.lq.librarytransaction.dto.BorrowRecordResponse;
import com.lq.librarytransaction.dto.FineResponse;
import com.lq.librarytransaction.dto.IssueRequest;
import com.lq.librarytransaction.dto.ReturnRequest;
import com.lq.librarytransaction.entity.BorrowRecord;
import com.lq.librarytransaction.entity.Fine;
import com.lq.librarytransaction.entity.TransactionHistory;
import com.lq.librarytransaction.exception.BookNotAvailableException;
import com.lq.librarytransaction.exception.BorrowLimitExceededException;
import com.lq.librarytransaction.repository.BorrowRecordRepository;
import com.lq.librarytransaction.repository.FineRepository;
import com.lq.librarytransaction.repository.TransactionHistoryRepository;
import com.lq.librarytransaction.service.BorrowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final FineRepository fineRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final BookServiceClient bookServiceClient;

    public BorrowServiceImpl(BorrowRecordRepository borrowRecordRepository,
                             FineRepository fineRepository,
                             TransactionHistoryRepository transactionHistoryRepository,
                             BookServiceClient bookServiceClient) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.fineRepository = fineRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.bookServiceClient = bookServiceClient;
    }

    @Override
    @Transactional
    public BorrowRecordResponse issueBook(IssueRequest request) {
        // 1. Check borrowing limits
        List<BorrowRecord> activeBorrows = borrowRecordRepository.findByUserIdAndStatus(request.getUserId(), BorrowRecord.BorrowStatus.ISSUED);
        if (activeBorrows.size() >= 5) {
            throw new BorrowLimitExceededException("User has reached maximum borrowing limit of 5 books.");
        }

        // 2. Fetch copy status from BookService via Feign Client
        Map<String, Object> copyDetails;
        try {
            copyDetails = bookServiceClient.getCopyByCode(request.getCopyCode());
        } catch (Exception e) {
            throw new BookNotAvailableException("Book copy " + request.getCopyCode() + " not found or unavailable.");
        }

        if (copyDetails == null || !"AVAILABLE".equalsIgnoreCase(String.valueOf(copyDetails.get("status")))) {
            throw new BookNotAvailableException("Physical book copy " + request.getCopyCode() + " is currently not available for issue.");
        }

        Long bookId = copyDetails.get("bookId") != null ? Long.valueOf(String.valueOf(copyDetails.get("bookId"))) : null;
        int days = request.getDurationDays() != null ? request.getDurationDays() : 14;

        BorrowRecord record = BorrowRecord.builder()
                .userId(request.getUserId())
                .copyCode(request.getCopyCode())
                .bookId(bookId)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(days))
                .status(BorrowRecord.BorrowStatus.ISSUED)
                .build();

        BorrowRecord savedRecord = borrowRecordRepository.save(record);

        // 3. Update physical copy status to BORROWED in BookService
        try {
            Long copyId = Long.valueOf(String.valueOf(copyDetails.get("id")));
            bookServiceClient.updateCopyStatus(copyId, "BORROWED");
        } catch (Exception ignored) {}

        // 4. Log transaction
        transactionHistoryRepository.save(TransactionHistory.builder()
                .userId(request.getUserId())
                .copyCode(request.getCopyCode())
                .action("ISSUE")
                .details("Book copy issued for " + days + " days. Due: " + record.getDueDate())
                .build());

        return toResponse(savedRecord);
    }

    @Override
    @Transactional
    public BorrowRecordResponse returnBook(ReturnRequest request) {
        BorrowRecord record = borrowRecordRepository.findByCopyCodeAndStatus(request.getCopyCode(), BorrowRecord.BorrowStatus.ISSUED)
                .orElseThrow(() -> new RuntimeException("No active borrow record found for copy code: " + request.getCopyCode()));

        record.setReturnDate(LocalDate.now());

        if (request.isLost()) {
            record.setStatus(BorrowRecord.BorrowStatus.LOST);
            Fine fine = Fine.builder()
                    .userId(record.getUserId())
                    .borrowRecord(record)
                    .amount(new BigDecimal("50.00"))
                    .reason("Lost book replacement fee")
                    .status(Fine.FineStatus.UNPAID)
                    .build();
            fineRepository.save(fine);
        } else if (request.isDamaged()) {
            record.setStatus(BorrowRecord.BorrowStatus.DAMAGED);
            Fine fine = Fine.builder()
                    .userId(record.getUserId())
                    .borrowRecord(record)
                    .amount(new BigDecimal("20.00"))
                    .reason("Damaged book repair fee")
                    .status(Fine.FineStatus.UNPAID)
                    .build();
            fineRepository.save(fine);
        } else {
            record.setStatus(BorrowRecord.BorrowStatus.RETURNED);
            // Calculate overdue fines if any
            if (LocalDate.now().isAfter(record.getDueDate())) {
                long daysOverdue = ChronoUnit.DAYS.between(record.getDueDate(), LocalDate.now());
                BigDecimal amount = new BigDecimal(daysOverdue * 2); // $2 per day
                Fine fine = Fine.builder()
                        .userId(record.getUserId())
                        .borrowRecord(record)
                        .amount(amount)
                        .reason("Overdue fine for " + daysOverdue + " days late return")
                        .status(Fine.FineStatus.UNPAID)
                        .build();
                fineRepository.save(fine);
            }
        }

        BorrowRecord saved = borrowRecordRepository.save(record);

        // Update physical copy status back in BookService
        try {
            Map<String, Object> copyDetails = bookServiceClient.getCopyByCode(request.getCopyCode());
            if (copyDetails != null && copyDetails.get("id") != null) {
                Long copyId = Long.valueOf(String.valueOf(copyDetails.get("id")));
                String newStatus = request.isLost() ? "LOST" : request.isDamaged() ? "DAMAGED" : "AVAILABLE";
                bookServiceClient.updateCopyStatus(copyId, newStatus);
            }
        } catch (Exception ignored) {}

        transactionHistoryRepository.save(TransactionHistory.builder()
                .userId(record.getUserId())
                .copyCode(request.getCopyCode())
                .action("RETURN")
                .details("Book returned. Status: " + record.getStatus())
                .build());

        return toResponse(saved);
    }

    @Override
    @Transactional
    public BorrowRecordResponse renewBook(Long borrowRecordId) {
        BorrowRecord record = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found with id: " + borrowRecordId));

        if (record.getStatus() != BorrowRecord.BorrowStatus.ISSUED) {
            throw new RuntimeException("Only active issued books can be renewed.");
        }

        if (record.getRenewalCount() >= 2) {
            throw new RuntimeException("Maximum renewal count (2) reached for this book.");
        }

        record.setDueDate(record.getDueDate().plusDays(14));
        record.setRenewalCount(record.getRenewalCount() + 1);

        BorrowRecord saved = borrowRecordRepository.save(record);

        transactionHistoryRepository.save(TransactionHistory.builder()
                .userId(record.getUserId())
                .copyCode(record.getCopyCode())
                .action("RENEW")
                .details("Renewal #" + record.getRenewalCount() + ". New Due Date: " + record.getDueDate())
                .build());

        return toResponse(saved);
    }

    @Override
    public List<BorrowRecordResponse> getUserBorrowHistory(Long userId) {
        return borrowRecordRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowRecordResponse> getActiveUserBorrows(Long userId) {
        return borrowRecordRepository.findByUserIdAndStatus(userId, BorrowRecord.BorrowStatus.ISSUED).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowRecordResponse> getAllOverdueBooks() {
        return borrowRecordRepository.findByStatus(BorrowRecord.BorrowStatus.ISSUED).stream()
                .filter(r -> LocalDate.now().isAfter(r.getDueDate()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FineResponse payFine(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new RuntimeException("Fine record not found with id: " + fineId));

        fine.setStatus(Fine.FineStatus.PAID);
        fine.setPaidAt(LocalDateTime.now());
        Fine saved = fineRepository.save(fine);

        return FineResponse.builder()
                .id(saved.getId())
                .userId(saved.getUserId())
                .borrowRecordId(saved.getBorrowRecord() != null ? saved.getBorrowRecord().getId() : null)
                .amount(saved.getAmount())
                .reason(saved.getReason())
                .status(saved.getStatus())
                .paidAt(saved.getPaidAt())
                .build();
    }

    @Override
    public List<FineResponse> getUserFines(Long userId) {
        return fineRepository.findByUserId(userId).stream()
                .map(f -> FineResponse.builder()
                        .id(f.getId())
                        .userId(f.getUserId())
                        .borrowRecordId(f.getBorrowRecord() != null ? f.getBorrowRecord().getId() : null)
                        .amount(f.getAmount())
                        .reason(f.getReason())
                        .status(f.getStatus())
                        .paidAt(f.getPaidAt())
                        .build())
                .collect(Collectors.toList());
    }

    private BorrowRecordResponse toResponse(BorrowRecord record) {
        boolean isOverdue = record.getStatus() == BorrowRecord.BorrowStatus.ISSUED && LocalDate.now().isAfter(record.getDueDate());
        return BorrowRecordResponse.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .copyCode(record.getCopyCode())
                .bookId(record.getBookId())
                .issueDate(record.getIssueDate())
                .dueDate(record.getDueDate())
                .returnDate(record.getReturnDate())
                .status(record.getStatus())
                .renewalCount(record.getRenewalCount())
                .isOverdue(isOverdue)
                .build();
    }
}
