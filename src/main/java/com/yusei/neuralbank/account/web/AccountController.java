package com.yusei.neuralbank.account.web;

import com.yusei.neuralbank.account.application.AccountService;
import com.yusei.neuralbank.account.domain.Account;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest request) {
        return AccountResponse.from(accountService.create(request.customerId()));
    }

    @GetMapping("/{accountId}")
    public AccountResponse get(@PathVariable UUID accountId) {
        return AccountResponse.from(accountService.get(accountId));
    }

    @PostMapping("/{accountId}/deposit")
    public AccountResponse deposit(@PathVariable UUID accountId, @Valid @RequestBody AmountRequest request) {
        return AccountResponse.from(accountService.deposit(accountId, request.amount()));
    }

    @PostMapping("/{accountId}/withdraw")
    public AccountResponse withdraw(@PathVariable UUID accountId, @Valid @RequestBody AmountRequest request) {
        return AccountResponse.from(accountService.withdraw(accountId, request.amount()));
    }

    public record CreateAccountRequest(@NotNull UUID customerId) {
    }

    public record AmountRequest(@NotNull @Positive BigDecimal amount) {
    }

    public record AccountResponse(UUID id, UUID customerId, BigDecimal balance, String status) {
        static AccountResponse from(Account account) {
            return new AccountResponse(
                    account.getId(),
                    account.getCustomer().getId(),
                    account.getBalance(),
                    account.getStatus().name()
            );
        }
    }
}
