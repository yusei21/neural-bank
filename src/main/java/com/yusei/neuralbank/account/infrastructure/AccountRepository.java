package com.yusei.neuralbank.account.infrastructure;

import com.yusei.neuralbank.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
