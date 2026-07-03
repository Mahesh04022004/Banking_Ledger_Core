package com.bank.banking_core.service.impl;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.dto.response.LedgerEntryResponse;
import com.bank.banking_core.dto.response.TransactionResponse;
import com.bank.banking_core.entity.Account;
import com.bank.banking_core.entity.LedgerEntry;
import com.bank.banking_core.exception.ResourceNotFoundException;
import com.bank.banking_core.mapper.LedgerMapper;
import com.bank.banking_core.repository.AccountRepository;
import com.bank.banking_core.repository.LedgerEntryRepository;
import com.bank.banking_core.service.LedgerQueryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LedgerQueryServiceImpl implements LedgerQueryService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final LedgerMapper ledgerMapper;

    public LedgerQueryServiceImpl(AccountRepository accountRepository,
                                  LedgerEntryRepository ledgerEntryRepository,
                                  LedgerMapper ledgerMapper) {

        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.ledgerMapper = ledgerMapper;
    }

    @Override
    public List<LedgerEntryResponse> getAccountStatement(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ApiMessages.ACCOUNT_NOT_FOUND));

        return ledgerEntryRepository
                .findByAccountOrderByCreatedAtDesc(account)
                .stream()
                .map(ledgerMapper::toResponse)
                .toList();
    }

    @Override
    public TransactionResponse getTransaction(String transactionReference) {

        List<LedgerEntry> entries =
                ledgerEntryRepository.findByTransactionReference(transactionReference);

        if (entries.isEmpty()) {
            throw new ResourceNotFoundException(ApiMessages.TRANSACTION_NOT_FOUND);
        }

        return ledgerMapper.toTransactionResponse(entries);
    }
}