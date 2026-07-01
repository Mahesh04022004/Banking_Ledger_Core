package com.bank.banking_core.service.impl;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.dto.request.CreateAccountRequest;
import com.bank.banking_core.dto.request.DepositRequest;
import com.bank.banking_core.dto.request.WithdrawRequest;
import com.bank.banking_core.dto.response.AccountResponse;
import com.bank.banking_core.exception.*;
import com.bank.banking_core.mapper.AccountMapper;
import com.bank.banking_core.repository.AccountRepository;
import com.bank.banking_core.service.AccountService;
import com.bank.banking_core.util.AccountNumberGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.bank.banking_core.entity.Account;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AccountMapper accountMapper;


    public AccountServiceImpl(AccountRepository accountRepository, AccountNumberGenerator accountNumberGenerator, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountNumberGenerator = accountNumberGenerator;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(ApiMessages.EMAIL_ALREADY_EXISTS);
        }

        Account account = accountMapper.toEntity(request);

        account.setAccountNumber(accountNumberGenerator.generate(account.getId()));
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

        account.setCurrentBalance(
                account.getCurrentBalance().add(request.getAmount())
        );

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse withdraw(Long accountId, WithdrawRequest request) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ApiMessages.ACCOUNT_NOT_FOUND));

        if (account.getCurrentBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(ApiMessages.INSUFFICIENT_BALANCE);
        }

        account.setCurrentBalance(
                account.getCurrentBalance().subtract(request.getAmount())
        );

        return accountMapper.toResponse(accountRepository.save(account));
    }


}
