package com.yusei.neuralbank.transaction.infrastructure;

import com.yusei.neuralbank.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByAccountIdOrderByCreatedAtDesc(UUID accountId);
}
