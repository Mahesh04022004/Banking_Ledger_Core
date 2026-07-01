package com.bank.banking_core.mapper;


import com.bank.banking_core.dto.request.CreateAccountRequest;
import com.bank.banking_core.dto.response.AccountResponse;
import com.bank.banking_core.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toEntity(CreateAccountRequest request) {

        Account account = new Account();

        account.setCustomerName(request.getCustomerName());
        account.setEmail(request.getEmail());
        account.setCurrentBalance(request.getInitialBalance());

        return account;
    }

    public AccountResponse toResponse(Account account) {

        AccountResponse response = new AccountResponse();

        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setCustomerName(account.getCustomerName());
        response.setEmail(account.getEmail());
        response.setCurrentBalance(account.getCurrentBalance());
        response.setCreatedAt(account.getCreatedAt());

        return response;
    }
}
