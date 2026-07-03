package com.bank.banking_core.constants;

public final class ApiMessages {

    private ApiMessages() {
    }

    // Success Messages
    public static final String ACCOUNT_CREATED =
            "Account created successfully.";

    public static final String ACCOUNT_FETCHED =
            "Account fetched successfully.";

    public static final String ACCOUNTS_FETCHED =
            "Accounts fetched successfully.";

    public static final String AMOUNT_DEPOSITED =
            "Amount deposited successfully.";

    public static final String AMOUNT_WITHDRAWN =
            "Amount withdrawn successfully.";

    // Error Messages
    public static final String ACCOUNT_NOT_FOUND =
            "Account not found.";

    public static final String EMAIL_ALREADY_EXISTS =
            "Email already exists.";

    public static final String INSUFFICIENT_BALANCE =
            "Insufficient balance.";

    public static final String VALIDATION_FAILED =
            "Validation failed.";

    public static final String INTERNAL_SERVER_ERROR =
            "Something went wrong.";

    public static final String TRANSFER_SUCCESSFUL =
            "Transfer completed successfully.";

    public static final String SAME_ACCOUNT_TRANSFER =
            "Sender and receiver accounts cannot be the same.";

    public static final String TRANSACTION_NOT_FOUND =
            "Transaction not found.";

    public static final String STATEMENT_FETCHED_SUCCESSFULLY =
            "Account statement fetched successfully.";

    public static final String TRANSACTION_FETCHED_SUCCESSFULLY =
            "Transaction fetched successfully.";

    public static final String LOCK_ACQUISITION_FAILED =
            "Unable to acquire distributed lock. Please retry the request.";
}
