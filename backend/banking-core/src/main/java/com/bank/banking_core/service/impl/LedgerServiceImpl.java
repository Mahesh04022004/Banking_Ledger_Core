package com.bank.banking_core.service.impl;

import com.bank.banking_core.entity.Account;
import com.bank.banking_core.entity.LedgerEntry;
import com.bank.banking_core.enums.EntryType;
import com.bank.banking_core.repository.LedgerEntryRepository;
import com.bank.banking_core.service.LedgerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LedgerServiceImpl implements LedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;

    public LedgerServiceImpl(LedgerEntryRepository ledgerEntryRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Override
    public void recordEntry(Account account,
                            String transactionReference,
                            EntryType entryType,
                            BigDecimal amount,
                            BigDecimal balanceBefore,
                            BigDecimal balanceAfter,
                            String description) {

        LedgerEntry ledgerEntry = new LedgerEntry();

        ledgerEntry.setAccount(account);
        ledgerEntry.setTransactionReference(transactionReference);
        ledgerEntry.setEntryType(entryType);
        ledgerEntry.setAmount(amount);
        ledgerEntry.setBalanceBefore(balanceBefore);
        ledgerEntry.setBalanceAfter(balanceAfter);
        ledgerEntry.setDescription(description);

        ledgerEntryRepository.save(ledgerEntry);
    }
}
