package com.ortech.bookstore.order.order_service.domain;

public record MyMessage(String routingKey, MyPayload payload) {}
