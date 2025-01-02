package com.ortech.bookstore.order.order_service.service;

import com.ortech.bookstore.order.order_service.config.ApplicationProperties;
import com.ortech.bookstore.order.order_service.domain.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final ApplicationProperties applicationProperties;

    public void publishEvent(OrderEvent orderEvent, String queueName) {
        log.error("Publish Event of Queue Name : {} ", queueName);
        rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(), queueName, orderEvent);
    }
}
