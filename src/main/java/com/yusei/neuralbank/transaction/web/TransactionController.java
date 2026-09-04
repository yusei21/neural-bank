package com.yusei.neuralbank.transaction.web;

import com.yusei.neuralbank.transaction.application.TransactionQueryService;
import com.yusei.neuralbank.transaction.domain.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/transactions")
public class TransactionController {

    private final TransactionQueryService transactionQueryService;

    public TransactionController(TransactionQueryService transactionQueryService) {
        this.transactionQueryService = transactionQueryService;
    }

    @GetMapping
    public List<TransactionResponse> findByAccount(@PathVariable UUID accountId) {
        return transactionQueryService.findByAccount(accountId).stream()
                .map(TransactionResponse::from)
                .toList();
    }

    public record TransactionResponse(
            UUID id,
            String type,
            BigDecimal amount,
            String description,
            OffsetDateTime createdAt
    ) {
        static TransactionResponse from(Transaction transaction) {
            return new TransactionResponse(
                    transaction.getId(),
                    transaction.getType().name(),
                    transaction.getAmount(),
                    transaction.getDescription(),
                    transaction.getCreatedAt()
            );
        }
    }
}
