package com.yusei.neuralbank.transaction.application;

import com.yusei.neuralbank.transaction.domain.Transaction;
import com.yusei.neuralbank.transaction.infrastructure.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionQueryService {

    private final TransactionRepository transactionRepository;

    public TransactionQueryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<Transaction> findByAccount(UUID accountId) {
        return transactionRepository.findAllByAccountIdOrderByCreatedAtDesc(accountId);
    }
}
