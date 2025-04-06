package com.ortech.bookstore.order.order_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ortech.bookstore.order.order_service.config.ApplicationProperties;
import com.ortech.bookstore.order.order_service.domain.CreateOrderRequest;
import com.ortech.bookstore.order.order_service.domain.CreateOrderResponse;
import com.ortech.bookstore.order.order_service.domain.MyMessage;
import com.ortech.bookstore.order.order_service.service.OrderService;
import com.ortech.bookstore.order.order_service.service.SecurityService;
package com.ortech.bookstore.order.order_service.domain.OrderSummary;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final RabbitTemplate rabbitTemplate;

    private final OrderService orderService;
    private final SecurityService securityService;

    private final ApplicationProperties applicationProperties;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CreateOrderResponse createOrder(@RequestBody @Valid CreateOrderRequest orderRequest)
            throws JsonProcessingException {
        return orderService.createOrder(securityService.getLoginUserName(), orderRequest);
    }

    @PostMapping("/send-order")
    void sendOrders(@RequestBody MyMessage myMessage) {
        rabbitTemplate.convertAndSend(
                applicationProperties.orderEventsExchange(), myMessage.routingKey(), myMessage.payload());
    }
    @GetMapping
    List<OrderSummary> getOrders()
    {
        return orderService.createOrder(securityService.getLoginUserName());
    }
}
