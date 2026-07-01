package com.bank.banking_core.controller;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.dto.request.CreateAccountRequest;
import com.bank.banking_core.dto.request.DepositRequest;
import com.bank.banking_core.dto.request.WithdrawRequest;
import com.bank.banking_core.dto.response.AccountResponse;
import com.bank.banking_core.response.ApiResponse;
import com.bank.banking_core.response.ApiResponseUtil;
import com.bank.banking_core.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        AccountResponse response = accountService.createAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseUtil.success(
                        ApiMessages.ACCOUNT_CREATED,
                        response
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(
            @PathVariable Long id) {

        AccountResponse response = accountService.getAccount(id);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        ApiMessages.ACCOUNT_FETCHED,
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {

        List<AccountResponse> response =
                accountService.getAllAccounts();

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        ApiMessages.ACCOUNT_FETCHED,
                        response
                )
        );
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<ApiResponse<AccountResponse>> deposit(
            @PathVariable Long id,
            @Valid @RequestBody DepositRequest request) {

        AccountResponse response =
                accountService.deposit(id, request);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        ApiMessages.AMOUNT_DEPOSITED,
                        response
                )
        );
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<ApiResponse<AccountResponse>> withdraw(
            @PathVariable Long id,
            @Valid @RequestBody WithdrawRequest request) {

        AccountResponse response =
                accountService.withdraw(id, request);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        ApiMessages.AMOUNT_WITHDRAWN,
                        response
                )
        );
    }

}
