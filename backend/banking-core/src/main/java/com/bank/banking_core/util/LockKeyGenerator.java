package com.bank.banking_core.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LockKeyGenerator {

    private static final String ACCOUNT_LOCK_PREFIX = "lock:account:";

    public String accountLock(String accountNumber) {
        return ACCOUNT_LOCK_PREFIX + accountNumber;
    }

    public List<String> accountLocks(String... accountNumbers) {

        return List.of(accountNumbers)
                .stream()
                .sorted()
                .map(this::accountLock)
                .toList();
    }
}