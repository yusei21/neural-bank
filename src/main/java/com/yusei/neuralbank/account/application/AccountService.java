package com.yusei.neuralbank.account.application;

import com.yusei.neuralbank.account.domain.Account;
import com.yusei.neuralbank.account.infrastructure.AccountRepository;
import com.yusei.neuralbank.customer.domain.Customer;
import com.yusei.neuralbank.customer.infrastructure.CustomerRepository;
import com.yusei.neuralbank.transaction.domain.Transaction;
import com.yusei.neuralbank.transaction.domain.TransactionType;
import com.yusei.neuralbank.transaction.infrastructure.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository,
            TransactionRepository transactionRepository
    ) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account create(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return accountRepository.save(Account.create(customer));
    }

    @Transactional(readOnly = true)
    public Account get(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Transactional
    public Account deposit(UUID accountId, BigDecimal amount) {
        Account account = get(accountId);
        account.deposit(amount);
        transactionRepository.save(Transaction.create(account, TransactionType.DEPOSIT, amount, "Deposit"));
        return account;
    }

    @Transactional
    public Account withdraw(UUID accountId, BigDecimal amount) {
        Account account = get(accountId);
        account.withdraw(amount);
        transactionRepository.save(Transaction.create(account, TransactionType.WITHDRAWAL, amount, "Withdrawal"));
        return account;
    }
}
