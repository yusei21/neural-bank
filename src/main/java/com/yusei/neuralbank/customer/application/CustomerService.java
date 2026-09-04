package com.yusei.neuralbank.customer.application;

import com.yusei.neuralbank.customer.domain.Customer;
import com.yusei.neuralbank.customer.infrastructure.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer create(String name, String email) {
        return customerRepository.save(Customer.create(name, email));
    }
}
