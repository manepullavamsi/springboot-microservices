package com.ortech.bookstore.order.order_service.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CreateOrderRequest(
        @Valid @NotEmpty(message = "OrdItem can't be empty") Set<OrderItem> items,
        @Valid Customer customer,
        @Valid DeliveryDetails deliveryDetails) {}
