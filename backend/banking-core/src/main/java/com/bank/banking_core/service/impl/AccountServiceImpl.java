package com.bank.banking_core.service.impl;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.dto.request.CreateAccountRequest;
import com.bank.banking_core.dto.request.DepositRequest;
import com.bank.banking_core.dto.request.WithdrawRequest;
import com.bank.banking_core.dto.response.AccountResponse;
import com.bank.banking_core.enums.EntryType;
import com.bank.banking_core.exception.*;
import com.bank.banking_core.mapper.AccountMapper;
import com.bank.banking_core.repository.AccountRepository;
import com.bank.banking_core.service.AccountService;
import com.bank.banking_core.service.LedgerService;
import com.bank.banking_core.util.AccountNumberGenerator;
import com.bank.banking_core.util.TransactionReferenceGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.bank.banking_core.entity.Account;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AccountMapper accountMapper;
    private final TransactionReferenceGenerator transactionReferenceGenerator;
    private final LedgerService ledgerService;

    public AccountServiceImpl(AccountRepository accountRepository, AccountNumberGenerator accountNumberGenerator, AccountMapper accountMapper, TransactionReferenceGenerator transactionReferenceGenerator, LedgerService ledgerService) {
        this.accountRepository = accountRepository;
        this.accountNumberGenerator = accountNumberGenerator;
        this.accountMapper = accountMapper;
        this.transactionReferenceGenerator = transactionReferenceGenerator;
        this.ledgerService = ledgerService;
    }

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(ApiMessages.EMAIL_ALREADY_EXISTS);
        }

        Account account = accountMapper.toEntity(request);

        account.setAccountNumber(accountNumberGenerator.generate());
        account.setCustomerName(request.getCustomerName());
        account.setEmail(request.getEmail());
        account.setCurrentBalance(request.getInitialBalance());

        Account savedAccount = accountRepository.save(account);

        return accountMapper.toResponse(savedAccount);
    }

    @Override
    @Transactional
    public AccountResponse getAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ApiMessages.ACCOUNT_NOT_FOUND));

        return accountMapper.toResponse(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    @Override
    public AccountResponse deposit(Long accountId, DepositRequest request) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ApiMessages.ACCOUNT_NOT_FOUND));

        // Generate transaction reference
        String transactionReference = transactionReferenceGenerator.generate();

        // Store balance before deposit
        BigDecimal balanceBefore = account.getCurrentBalance();

        // Update balance
        account.setCurrentBalance(
                balanceBefore.add(request.getAmount())
        );

        // Save updated account
        accountRepository.save(account);

        // Record ledger entry
        ledgerService.recordEntry(
                account,
                transactionReference,
                EntryType.CREDIT,
                request.getAmount(),
                balanceBefore,
                account.getCurrentBalance(),
                ApiMessages.AMOUNT_DEPOSITED
        );

        return accountMapper.toResponse(account);
    }

    @Override
    public AccountResponse withdraw(Long accountId, WithdrawRequest request) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ApiMessages.ACCOUNT_NOT_FOUND));

        // Check sufficient balance
        if (account.getCurrentBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(ApiMessages.INSUFFICIENT_BALANCE);
        }

        // Generate transaction reference
        String transactionReference = transactionReferenceGenerator.generate();

        // Store balance before withdrawal
        BigDecimal balanceBefore = account.getCurrentBalance();

        // Update balance
        account.setCurrentBalance(
                balanceBefore.subtract(request.getAmount())
        );

        // Save updated account
        accountRepository.save(account);

        // Record ledger entry
        ledgerService.recordEntry(
                account,
                transactionReference,
                EntryType.DEBIT,
                request.getAmount(),
                balanceBefore,
                account.getCurrentBalance(),
                ApiMessages.AMOUNT_WITHDRAWN
        );

        return accountMapper.toResponse(account);
    }


}
