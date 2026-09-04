package com.yusei.neuralbank.customer.infrastructure;

import com.yusei.neuralbank.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
