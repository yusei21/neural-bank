package com.yusei.neuralbank.transaction.application;

import com.yusei.neuralbank.account.domain.Account;
import com.yusei.neuralbank.account.infrastructure.AccountRepository;
import com.yusei.neuralbank.transaction.domain.Transaction;
import com.yusei.neuralbank.transaction.domain.TransactionType;
import com.yusei.neuralbank.transaction.infrastructure.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transfer(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("Source and destination accounts must be different");
        }

        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account destination = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        source.withdraw(amount);
        destination.deposit(amount);

        transactionRepository.save(Transaction.create(source, TransactionType.TRANSFER_OUT, amount, "Transfer to " + destinationAccountId));
        transactionRepository.save(Transaction.create(destination, TransactionType.TRANSFER_IN, amount, "Transfer from " + sourceAccountId));
    }
}
