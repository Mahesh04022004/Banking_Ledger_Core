package com.bank.banking_core.mapper;

import com.bank.banking_core.dto.response.TransferResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransferMapper {

    public TransferResponse toResponse(String reference,
                                       String fromAccount,
                                       String toAccount,
                                       BigDecimal amount) {

        TransferResponse response = new TransferResponse();

        response.setTransactionReference(reference);
        response.setFromAccountNumber(fromAccount);
        response.setToAccountNumber(toAccount);
        response.setAmount(amount);
        response.setTransferredAt(LocalDateTime.now());

        return response;
    }
}
