package com.bank.banking_core.mapper;

import com.bank.banking_core.dto.response.LedgerEntryResponse;
import com.bank.banking_core.dto.response.TransactionResponse;
import com.bank.banking_core.entity.LedgerEntry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LedgerMapper {

    public LedgerEntryResponse toResponse(LedgerEntry ledgerEntry) {

        LedgerEntryResponse response = new LedgerEntryResponse();

        response.setAccountNumber(
                ledgerEntry.getAccount().getAccountNumber());

        response.setTransactionReference(
                ledgerEntry.getTransactionReference());

        response.setEntryType(
                ledgerEntry.getEntryType());

        response.setAmount(
                ledgerEntry.getAmount());

        response.setBalanceBefore(
                ledgerEntry.getBalanceBefore());

        response.setBalanceAfter(
                ledgerEntry.getBalanceAfter());

        response.setDescription(
                ledgerEntry.getDescription());

        response.setCreatedAt(
                ledgerEntry.getCreatedAt());

        return response;
    }

    public TransactionResponse toTransactionResponse(List<LedgerEntry> entries) {

        TransactionResponse response = new TransactionResponse();

        if (entries == null || entries.isEmpty()) {
            return response;
        }

        response.setTransactionReference(
                entries.getFirst().getTransactionReference());

        response.setTransactionTime(
                entries.getFirst().getCreatedAt());

        response.setEntries(
                entries.stream()
                        .map(this::toResponse)
                        .toList());

        return response;
    }
}