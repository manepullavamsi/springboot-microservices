package com.ortech.bookstore.order.order_service.domain;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderEvent(
        String eventId,
        String orderNumber,
        OrderEventType orderType,
        Set<OrderItem> items,
        Customer customer,
        DeliveryDetails deliveryDetails,
        LocalDateTime createdAt) {}
