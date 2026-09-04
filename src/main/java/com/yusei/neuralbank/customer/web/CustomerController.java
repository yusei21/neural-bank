package com.yusei.neuralbank.customer.web;

import com.yusei.neuralbank.customer.application.CustomerService;
import com.yusei.neuralbank.customer.domain.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@Valid @RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.create(request.name(), request.email());
        return CustomerResponse.from(customer);
    }

    public record CreateCustomerRequest(
            @NotBlank String name,
            @NotBlank @Email String email
    ) {
    }

    public record CustomerResponse(UUID id, String name, String email, OffsetDateTime createdAt) {
        static CustomerResponse from(Customer customer) {
            return new CustomerResponse(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getCreatedAt()
            );
        }
    }
}
