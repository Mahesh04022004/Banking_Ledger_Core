package com.bank.banking_core.service.impl;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.dto.request.TransferRequest;
import com.bank.banking_core.dto.response.TransferResponse;
import com.bank.banking_core.entity.Account;
import com.bank.banking_core.exception.BadRequestException;
import com.bank.banking_core.exception.InsufficientBalanceException;
import com.bank.banking_core.exception.ResourceNotFoundException;
import com.bank.banking_core.mapper.TransferMapper;
import com.bank.banking_core.repository.AccountRepository;
import com.bank.banking_core.service.TransferService;
import com.bank.banking_core.util.TransactionReferenceGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransactionReferenceGenerator transactionReferenceGenerator;
    private final TransferMapper transferMapper;

    public TransferServiceImpl(AccountRepository accountRepository, TransactionReferenceGenerator transactionReferenceGenerator, TransferMapper transferMapper) {
        this.accountRepository = accountRepository;
        this.transactionReferenceGenerator = transactionReferenceGenerator;
        this.transferMapper = transferMapper;
    }

    @Override
    public TransferResponse transfer(TransferRequest request) {

        Account sender = getAccountByNumber(request.getFromAccountNumber());

        Account receiver = getAccountByNumber(request.getToAccountNumber());

        // Cannot transfer to same account
        if (sender.getAccountNumber().equals(receiver.getAccountNumber())) {
            throw new BadRequestException("Sender and receiver accounts cannot be the same.");
        }

        // Sufficient balance check
        if (sender.getCurrentBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(ApiMessages.INSUFFICIENT_BALANCE);
        }

        // Debit sender
        sender.setCurrentBalance(
                sender.getCurrentBalance().subtract(request.getAmount())
        );

        // Credit receiver
        receiver.setCurrentBalance(
                receiver.getCurrentBalance().add(request.getAmount())
        );

        accountRepository.save(sender);
        accountRepository.save(receiver);

        TransferResponse response = new TransferResponse();

        return transferMapper.toResponse(
                transactionReferenceGenerator.generate(),
                sender.getAccountNumber(),
                receiver.getAccountNumber(),
                request.getAmount()
        );
    }

    private Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ApiMessages.ACCOUNT_NOT_FOUND));
    }

}
