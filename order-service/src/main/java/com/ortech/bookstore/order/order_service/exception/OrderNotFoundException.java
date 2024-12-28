package com.ortech.bookstore.order.order_service.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException forOrderId(String orderId) {
        return new OrderNotFoundException("Order Not Found for OrderId: " + orderId);
    }
}
