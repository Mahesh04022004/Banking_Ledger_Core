package com.bank.banking_core.service.impl;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.dto.request.TransferRequest;
import com.bank.banking_core.dto.response.TransferResponse;
import com.bank.banking_core.entity.Account;
import com.bank.banking_core.enums.EntryType;
import com.bank.banking_core.exception.BadRequestException;
import com.bank.banking_core.exception.InsufficientBalanceException;
import com.bank.banking_core.exception.ResourceNotFoundException;
import com.bank.banking_core.mapper.TransferMapper;
import com.bank.banking_core.repository.AccountRepository;
import com.bank.banking_core.service.DistributedLockService;
import com.bank.banking_core.service.LedgerService;
import com.bank.banking_core.service.TransferService;
import com.bank.banking_core.util.LockKeyGenerator;
import com.bank.banking_core.util.TransactionReferenceGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransactionReferenceGenerator transactionReferenceGenerator;
    private final TransferMapper transferMapper;
    private final LedgerService ledgerService;
    private final DistributedLockService distributedLockService;
    private final LockKeyGenerator lockKeyGenerator;

    public TransferServiceImpl(AccountRepository accountRepository, TransactionReferenceGenerator transactionReferenceGenerator, TransferMapper transferMapper, LedgerService ledgerService, DistributedLockService distributedLockService, LockKeyGenerator lockKeyGenerator) {
        this.accountRepository = accountRepository;
        this.transactionReferenceGenerator = transactionReferenceGenerator;
        this.transferMapper = transferMapper;
        this.ledgerService = ledgerService;
        this.distributedLockService = distributedLockService;
        this.lockKeyGenerator = lockKeyGenerator;
    }

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        List<String> lockKeys = lockKeyGenerator.accountLocks(
                request.getFromAccountNumber(),
                request.getToAccountNumber()
        );

        return distributedLockService.executeWithLocks(lockKeys, () -> {

            Account sender = getAccountByNumber(request.getFromAccountNumber());
            Account receiver = getAccountByNumber(request.getToAccountNumber());

            // Cannot transfer to the same account
            if (sender.getAccountNumber().equals(receiver.getAccountNumber())) {
                throw new BadRequestException(ApiMessages.SAME_ACCOUNT_TRANSFER);
            }

            // Check sufficient balance
            if (sender.getCurrentBalance().compareTo(request.getAmount()) < 0) {
                throw new InsufficientBalanceException(ApiMessages.INSUFFICIENT_BALANCE);
            }

            // Generate transaction reference
            String transactionReference = transactionReferenceGenerator.generate();

            // Store balances before transaction
            BigDecimal senderBalanceBefore = sender.getCurrentBalance();
            BigDecimal receiverBalanceBefore = receiver.getCurrentBalance();

            // Update balances
            sender.setCurrentBalance(
                    senderBalanceBefore.subtract(request.getAmount())
            );

            receiver.setCurrentBalance(
                    receiverBalanceBefore.add(request.getAmount())
            );

            // Save updated accounts
            accountRepository.saveAll(List.of(sender, receiver));

            // Record sender ledger entry
            ledgerService.recordEntry(
                    sender,
                    transactionReference,
                    EntryType.DEBIT,
                    request.getAmount(),
                    senderBalanceBefore,
                    sender.getCurrentBalance(),
                    "Transfer to " + receiver.getAccountNumber()
            );

            // Record receiver ledger entry
            ledgerService.recordEntry(
                    receiver,
                    transactionReference,
                    EntryType.CREDIT,
                    request.getAmount(),
                    receiverBalanceBefore,
                    receiver.getCurrentBalance(),
                    "Transfer from " + sender.getAccountNumber()
            );

            return transferMapper.toResponse(
                    transactionReference,
                    sender.getAccountNumber(),
                    receiver.getAccountNumber(),
                    request.getAmount()
            );
        });
    }

    private Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ApiMessages.ACCOUNT_NOT_FOUND));
    }

}
