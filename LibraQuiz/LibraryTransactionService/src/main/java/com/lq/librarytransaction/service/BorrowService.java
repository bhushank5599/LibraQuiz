package com.lq.librarytransaction.service;

import com.lq.librarytransaction.dto.BorrowRecordResponse;
import com.lq.librarytransaction.dto.FineResponse;
import com.lq.librarytransaction.dto.IssueRequest;
import com.lq.librarytransaction.dto.ReturnRequest;

import java.util.List;

public interface BorrowService {
    BorrowRecordResponse issueBook(IssueRequest request);
    BorrowRecordResponse returnBook(ReturnRequest request);
    BorrowRecordResponse renewBook(Long borrowRecordId);
    List<BorrowRecordResponse> getUserBorrowHistory(Long userId);
    List<BorrowRecordResponse> getActiveUserBorrows(Long userId);
    List<BorrowRecordResponse> getAllOverdueBooks();
    FineResponse payFine(Long fineId);
    List<FineResponse> getUserFines(Long userId);
}
