package com.ortech.bookstore.order.order_service.service;

import com.ortech.bookstore.order.order_service.domain.CreateOrderRequest;
import com.ortech.bookstore.order.order_service.domain.OrderItem;
import com.ortech.bookstore.order.order_service.exception.InValidItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderValidatorService {

    private final ProductServiceClient productServiceClient;

    public void validateOrder(CreateOrderRequest request) {
        for (OrderItem item : request.items()) {
            var product = productServiceClient
                    .getProductByCode(item.code())
                    .orElseThrow(() -> new InValidItemException(
                            "InValid Item Code Doesn't Exist at Catalog Service for Item Code : " + item.code()));
            if (product.price().compareTo(item.price()) != 0) {
                throw new InValidItemException("InValid Item Price for Item Code : " + item.code());
            }
        }
    }
}
