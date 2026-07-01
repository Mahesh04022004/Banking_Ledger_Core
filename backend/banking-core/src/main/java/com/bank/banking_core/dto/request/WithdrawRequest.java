package com.bank.banking_core.dto.request;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class WithdrawRequest {

    @DecimalMin(value = "0.01", message = "Withdrawal amount must be greater than zero.")
    private BigDecimal amount;

    public WithdrawRequest() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
