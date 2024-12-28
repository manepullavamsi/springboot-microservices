package com.ortech.bookstore.order.order_service.exception;

public class InValidItemException extends RuntimeException{

    public InValidItemException(String message)
    {
        super(message);
    }
}
