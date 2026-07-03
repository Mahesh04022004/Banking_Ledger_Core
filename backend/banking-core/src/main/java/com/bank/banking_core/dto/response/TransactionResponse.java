package com.bank.banking_core.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionResponse {

    private String transactionReference;

    private LocalDateTime transactionTime;

    private List<LedgerEntryResponse> entries;

    // getters & setters

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public List<LedgerEntryResponse> getEntries() {
        return entries;
    }

    public void setEntries(List<LedgerEntryResponse> entries) {
        this.entries = entries;
    }
}