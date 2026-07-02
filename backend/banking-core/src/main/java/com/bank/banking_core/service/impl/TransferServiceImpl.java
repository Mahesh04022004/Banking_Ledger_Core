package com.bank.banking_core.service.impl;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.dto.request.TransferRequest;
import com.bank.banking_core.dto.response.TransferResponse;
import com.bank.banking_core.entity.Account;
import com.bank.banking_core.exception.ResourceNotFoundException;
import com.bank.banking_core.repository.AccountRepository;
import com.bank.banking_core.service.TransferService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;

    public TransferServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public TransferResponse transfer(TransferRequest request) {

        return null;
    }

    private Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ApiMessages.ACCOUNT_NOT_FOUND));
    }

}
