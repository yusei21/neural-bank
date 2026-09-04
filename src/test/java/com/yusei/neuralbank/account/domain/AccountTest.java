package com.yusei.neuralbank.account.domain;

import com.yusei.neuralbank.customer.domain.Customer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void shouldDepositAndWithdraw() {
        Customer customer = Customer.create("Test User", "test@example.com");
        Account account = Account.create(customer);

        account.deposit(new BigDecimal("100.00"));
        account.withdraw(new BigDecimal("40.00"));

        assertEquals(new BigDecimal("60.00"), account.getBalance());
    }

    @Test
    void shouldRejectWithdrawalWhenBalanceIsInsufficient() {
        Customer customer = Customer.create("Test User", "test@example.com");
        Account account = Account.create(customer);

        assertThrows(IllegalStateException.class,
                () -> account.withdraw(new BigDecimal("1.00")));
    }
}
