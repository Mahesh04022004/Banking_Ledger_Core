package com.bank.banking_core.util;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {

    private static final String PREFIX = "ACC";

    public String generate(Long id) {
        return PREFIX + String.format("%08d", id);
    }

}
