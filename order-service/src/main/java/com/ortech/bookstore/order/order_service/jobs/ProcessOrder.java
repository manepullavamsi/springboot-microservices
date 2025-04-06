package com.ortech.bookstore.order.order_service.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ortech.bookstore.order.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessOrder {

    private final OrderService orderService;

    @Scheduled(cron = "${orders.process-order-events-job-cron}")
    @SchedulerLock(name = "OrderProcessing")
    public void processOrder() throws JsonProcessingException {
         LockAssert.assertLocked();
        log.info("Process Order Started ");
        orderService.processOrder();
    }
}
