package com.bank.banking_core.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TransactionReferenceGenerator {

    private static final String PREFIX = "TRX";

    public String generate() {
        return PREFIX + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}
