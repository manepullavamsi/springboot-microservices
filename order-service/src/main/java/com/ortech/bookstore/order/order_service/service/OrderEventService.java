package com.ortech.bookstore.order.order_service.service;

import static com.ortech.bookstore.order.order_service.utlity.OrderMapper.fromJsonPayload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ortech.bookstore.order.order_service.config.ApplicationProperties;
import com.ortech.bookstore.order.order_service.domain.*;
import com.ortech.bookstore.order.order_service.repository.OrderEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderEventService {

    private final OrderEventRepository orderEventRepository;

    private final ObjectMapper objectMapper;

    private final OrderEventPublisher orderEventPublisher;

    private final ApplicationProperties applicationProperties;

    public void saveOrderEvent(OrderEvent orderEvent) throws JsonProcessingException {
        OrderEventsEntity orderEventsEntity = new OrderEventsEntity();
        orderEventsEntity.setOrderNumber(orderEvent.orderNumber());
        orderEventsEntity.setEventId(orderEvent.eventId());
        orderEventsEntity.setEventType(orderEvent.orderType());
        orderEventsEntity.setPayload(objectMapper.writeValueAsString(orderEvent));
        orderEventsEntity.setCreatedAt(orderEvent.createdAt());
        orderEventRepository.save(orderEventsEntity);
        log.info("Order Event has been stored into Order Event Table for orderID {} : ", orderEvent.orderNumber());
    }

    public void publishEvent() {
        Sort createdAtSort = Sort.by("createdAt").ascending();
        List<OrderEventsEntity> orderEventsEntityList = orderEventRepository.findAll(createdAtSort);
        log.info("Order Event Count : {} ", orderEventsEntityList.size());
        for (OrderEventsEntity orderEventsEntity : orderEventsEntityList) {
            publishEvent(orderEventsEntity);
            orderEventRepository.delete(orderEventsEntity);
        }
    }

    private void publishEvent(OrderEventsEntity orderEventsEntity) {
        OrderEvent orderEvent = fromJsonPayload(orderEventsEntity.getPayload(), OrderEvent.class);
        switch (orderEvent.orderType()) {
            case ORDER_CREATED:
                orderEventPublisher.publishEvent(orderEvent, applicationProperties.newOrdersQueue());
                break;
            case ORDER_CANCELLED:
                orderEventPublisher.publishEvent(orderEvent, applicationProperties.cancelledOrdersQueue());
                break;
            case ORDER_DELIVERED:
                orderEventPublisher.publishEvent(orderEvent, applicationProperties.deliveredOrdersQueue());
                break;
            case ORDER_PROCESSING_FAILED:
                orderEventPublisher.publishEvent(orderEvent, applicationProperties.errorOrdersQueue());
                break;
            default:
                log.error("Un Supported Event Type : {} ", orderEvent.orderType());
                break;
        }
    }
}
