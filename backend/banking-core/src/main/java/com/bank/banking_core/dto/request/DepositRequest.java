package com.bank.banking_core.dto.request;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class DepositRequest {

    @DecimalMin(value = "0.01", message = "Deposit amount must be greater than zero.")
    private BigDecimal amount;

    public DepositRequest() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
