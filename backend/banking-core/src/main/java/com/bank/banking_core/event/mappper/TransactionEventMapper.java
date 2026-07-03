package com.bank.banking_core.event.mappper;

import com.bank.banking_core.event.model.TransactionEvent;
import com.bank.banking_core.event.model.TransactionEventType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransactionEventMapper {

    public TransactionEvent transferEvent(
            String transactionReference,
            String fromAccount,
            String toAccount,
            BigDecimal amount
    ) {

        TransactionEvent event = new TransactionEvent();

        event.setTransactionReference(transactionReference);
        event.setEventType(TransactionEventType.TRANSFER);
        event.setFromAccount(fromAccount);
        event.setToAccount(toAccount);
        event.setAmount(amount);
        event.setTimestamp(LocalDateTime.now());

        return event;
    }

    public TransactionEvent depositEvent(
            String transactionReference,
            String accountNumber,
            BigDecimal amount
    ) {

        TransactionEvent event = new TransactionEvent();

        event.setTransactionReference(transactionReference);
        event.setEventType(TransactionEventType.DEPOSIT);
        event.setFromAccount(null);
        event.setToAccount(accountNumber);
        event.setAmount(amount);
        event.setTimestamp(LocalDateTime.now());

        return event;
    }

    public TransactionEvent withdrawEvent(
            String transactionReference,
            String accountNumber,
            BigDecimal amount
    ) {

        TransactionEvent event = new TransactionEvent();

        event.setTransactionReference(transactionReference);
        event.setEventType(TransactionEventType.WITHDRAW);
        event.setFromAccount(accountNumber);
        event.setToAccount(null);
        event.setAmount(amount);
        event.setTimestamp(LocalDateTime.now());

        return event;
    }
}
