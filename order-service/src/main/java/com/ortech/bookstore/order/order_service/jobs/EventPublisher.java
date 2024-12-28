package com.ortech.bookstore.order.order_service.jobs;

import com.ortech.bookstore.order.order_service.config.ApplicationProperties;
import com.ortech.bookstore.order.order_service.repository.OrderEventRepository;
import com.ortech.bookstore.order.order_service.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {

    private final OrderEventService orderEventService;


    @Scheduled(cron = "${orders.publish-order-events-job-cron}")
    public void scheduledPublisher()
    {
        log.info("Publishing Order Events at {}", Instant.now());
        orderEventService.publishEvent();
    }
}
