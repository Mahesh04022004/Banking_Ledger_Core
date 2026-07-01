package com.bank.banking_core.repository;


import com.bank.banking_core.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByAccountNumber(String accountNumber);

}
