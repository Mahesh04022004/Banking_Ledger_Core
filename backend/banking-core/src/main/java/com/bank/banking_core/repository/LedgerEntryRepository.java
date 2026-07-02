package com.bank.banking_core.repository;

import com.bank.banking_core.entity.Account;
import com.bank.banking_core.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {

    List<LedgerEntry> findByAccountOrderByCreatedAtDesc(Account account);

    List<LedgerEntry> findByTransactionReference(String transactionReference);
}
