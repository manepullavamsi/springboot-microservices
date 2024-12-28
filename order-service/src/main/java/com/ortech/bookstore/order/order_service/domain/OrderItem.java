package com.ortech.bookstore.order.order_service.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderItem(
        @NotBlank(message = "Code is required ") String code,
        @NotBlank(message = "Name is required ") String name,
        @NotNull(message = "Price is Required") BigDecimal price,
        @NotNull @Min(1) Integer quantity) {}
