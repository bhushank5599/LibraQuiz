package com.lq.librarytransaction.controller;

import com.lq.librarytransaction.dto.BorrowRecordResponse;
import com.lq.librarytransaction.dto.FineResponse;
import com.lq.librarytransaction.dto.IssueRequest;
import com.lq.librarytransaction.dto.ReturnRequest;
import com.lq.librarytransaction.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final BorrowService borrowService;

    public TransactionController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/issue")
    public ResponseEntity<BorrowRecordResponse> issueBook(@Valid @RequestBody IssueRequest request) {
        return new ResponseEntity<>(borrowService.issueBook(request), HttpStatus.CREATED);
    }

    @PostMapping("/return")
    public ResponseEntity<BorrowRecordResponse> returnBook(@Valid @RequestBody ReturnRequest request) {
        return ResponseEntity.ok(borrowService.returnBook(request));
    }

    @PostMapping("/renew/{borrowRecordId}")
    public ResponseEntity<BorrowRecordResponse> renewBook(@PathVariable Long borrowRecordId) {
        return ResponseEntity.ok(borrowService.renewBook(borrowRecordId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowRecordResponse>> getUserBorrowHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getUserBorrowHistory(userId));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<BorrowRecordResponse>> getActiveUserBorrows(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getActiveUserBorrows(userId));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowRecordResponse>> getAllOverdueBooks() {
        return ResponseEntity.ok(borrowService.getAllOverdueBooks());
    }

    @GetMapping("/fines/user/{userId}")
    public ResponseEntity<List<FineResponse>> getUserFines(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getUserFines(userId));
    }

    @PostMapping("/fines/{fineId}/pay")
    public ResponseEntity<FineResponse> payFine(@PathVariable Long fineId) {
        return ResponseEntity.ok(borrowService.payFine(fineId));
    }
}
