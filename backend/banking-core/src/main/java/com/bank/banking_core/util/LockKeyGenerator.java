package com.bank.banking_core.util;

import org.springframework.stereotype.Component;

@Component
public class LockKeyGenerator {

    private static final String ACCOUNT_LOCK_PREFIX = "lock:account:";

    public String accountLock(String accountNumber) {
        return ACCOUNT_LOCK_PREFIX + accountNumber;
    }

}