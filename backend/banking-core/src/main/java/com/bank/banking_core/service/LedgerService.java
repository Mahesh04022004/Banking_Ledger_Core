package com.bank.banking_core.service;

import com.bank.banking_core.entity.Account;
import com.bank.banking_core.enums.EntryType;

import java.math.BigDecimal;

public interface LedgerService {

    void recordEntry(
            Account account,
            String transactionReference,
            EntryType entryType,
            BigDecimal amount,
            BigDecimal balanceBefore,
            BigDecimal balanceAfter,
            String description
    );

}