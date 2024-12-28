package com.ortech.bookstore.order.order_service.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Customer(
        @NotBlank(message = "name can't be empty") String name,
        @NotBlank(message = "email can't be empty") @Email String email,
        @NotBlank(message = "phoneNumber can't be empty")
                @Size(min = 10, max = 13, message = "phoneNumber length should be in between 10 to 13.")
                String phoneNumber) {}
