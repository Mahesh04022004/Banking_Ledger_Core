package com.bank.banking_core.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountNumberGenerator {

    public String generate() {
        return "ACC" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}
