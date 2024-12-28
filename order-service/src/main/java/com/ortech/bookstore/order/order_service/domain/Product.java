package com.ortech.bookstore.order.order_service.domain;

import java.math.BigDecimal;

public record Product(
        String code,
        String name,
        String description,
        String image_url,
        BigDecimal price) {
}