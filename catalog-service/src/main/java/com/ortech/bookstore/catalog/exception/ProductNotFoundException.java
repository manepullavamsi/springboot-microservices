package com.ortech.bookstore.catalog.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String code) {
        super(code);
    }

    public static ProductNotFoundException forCode(String code) {
        return new ProductNotFoundException("Product Not for the code: " + code);
    }
}
