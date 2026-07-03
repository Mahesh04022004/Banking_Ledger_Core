package com.bank.banking_core.controller;

import com.bank.banking_core.dto.response.LedgerEntryResponse;
import com.bank.banking_core.dto.response.TransactionResponse;
import com.bank.banking_core.response.ApiResponse;
import com.bank.banking_core.response.ApiResponseUtil;
import com.bank.banking_core.service.LedgerQueryService;
import com.bank.banking_core.constants.ApiMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ledger")
public class LedgerController {

    private final LedgerQueryService ledgerQueryService;

    public LedgerController(LedgerQueryService ledgerQueryService) {
        this.ledgerQueryService = ledgerQueryService;
    }

    @GetMapping("/accounts/{accountNumber}/statement")
    public ResponseEntity<ApiResponse<List<LedgerEntryResponse>>> getAccountStatement(
            @PathVariable String accountNumber) {

        List<LedgerEntryResponse> response =
                ledgerQueryService.getAccountStatement(accountNumber);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        ApiMessages.STATEMENT_FETCHED_SUCCESSFULLY,
                        response
                )
        );
    }

    @GetMapping("/transactions/{transactionReference}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransaction(
            @PathVariable String transactionReference) {

        TransactionResponse response =
                ledgerQueryService.getTransaction(transactionReference);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        ApiMessages.TRANSACTION_FETCHED_SUCCESSFULLY,
                        response
                )
        );
    }
}