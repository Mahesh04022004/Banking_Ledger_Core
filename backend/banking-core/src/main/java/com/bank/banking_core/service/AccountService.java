package com.bank.banking_core.service;

import com.bank.banking_core.dto.request.CreateAccountRequest;
import com.bank.banking_core.dto.request.DepositRequest;
import com.bank.banking_core.dto.request.WithdrawRequest;
import com.bank.banking_core.dto.response.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);

    AccountResponse getAccount(Long accountId);

    List<AccountResponse> getAllAccounts();

    AccountResponse deposit(Long accountId, DepositRequest request);

    AccountResponse withdraw(Long accountId, WithdrawRequest request);

}