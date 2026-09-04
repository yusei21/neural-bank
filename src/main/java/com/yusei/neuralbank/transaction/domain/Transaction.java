package com.yusei.neuralbank.transaction.domain;

import com.yusei.neuralbank.account.domain.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(length = 255)
    private String description;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    protected Transaction() {
    }

    public Transaction(UUID id, Account account, TransactionType type, BigDecimal amount, String description, OffsetDateTime createdAt) {
        this.id = id;
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static Transaction create(Account account, TransactionType type, BigDecimal amount, String description) {
        return new Transaction(UUID.randomUUID(), account, type, amount, description, OffsetDateTime.now());
    }

    public UUID getId() { return id; }
    public Account getAccount() { return account; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
