package com.bank.banking_core.service;

import com.bank.banking_core.dto.response.LedgerEntryResponse;
import com.bank.banking_core.dto.response.TransactionResponse;

import java.util.List;

public interface LedgerQueryService {

    List<LedgerEntryResponse> getAccountStatement(String accountNumber);

    TransactionResponse getTransaction(String transactionReference);

}