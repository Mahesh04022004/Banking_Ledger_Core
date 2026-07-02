package com.bank.banking_core.controller;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.dto.request.TransferRequest;
import com.bank.banking_core.dto.response.TransferResponse;
import com.bank.banking_core.response.ApiResponse;
import com.bank.banking_core.response.ApiResponseUtil;
import com.bank.banking_core.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransferResponse>> transfer(
            @Valid @RequestBody TransferRequest request) {

        TransferResponse response = transferService.transfer(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseUtil.success(
                        ApiMessages.TRANSFER_SUCCESSFUL,
                        response
                ));
    }
}
