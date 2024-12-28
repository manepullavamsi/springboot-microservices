package com.ortech.bookstore.order.order_service.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ortech.bookstore.order.order_service.service.OrderEventService;
import com.ortech.bookstore.order.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessOrder {

    private final OrderService orderService;

    @Scheduled(cron = "${orders.process-order-events-job-cron}")
    public void processOrder() throws JsonProcessingException {
        log.info("Process Order Started ");
        orderService.processOrder();
    }

}
