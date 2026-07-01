package com.bank.banking_core.util;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {

    public String generate() {

        return "ACC" + System.currentTimeMillis();

    }

}
