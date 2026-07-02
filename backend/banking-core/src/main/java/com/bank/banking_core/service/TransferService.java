package com.bank.banking_core.service;

import com.bank.banking_core.dto.request.TransferRequest;
import com.bank.banking_core.dto.response.TransferResponse;

public interface TransferService {
    TransferResponse transfer(TransferRequest request);
}
