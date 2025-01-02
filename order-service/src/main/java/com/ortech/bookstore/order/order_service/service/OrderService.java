package com.ortech.bookstore.order.order_service.service;

import static com.ortech.bookstore.order.order_service.utlity.OrderMapper.buildOrderEvent;
import static com.ortech.bookstore.order.order_service.utlity.Utility.ValidCountryList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ortech.bookstore.order.order_service.domain.*;
import com.ortech.bookstore.order.order_service.utlity.OrderMapper;
import com.ortech.bookstore.order.order_service.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderValidatorService orderValidatorService;

    private final OrderEventService orderEventService;

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) throws JsonProcessingException {
        orderValidatorService.validateOrder(request);
        log.info(" Create Oder Validation Completed Successfully. ");
        OrderEntity newOrder = OrderMapper.createOrderRequestMapper(request);
        newOrder.setUsername(userName);
        OrderEntity orderEntity = orderRepository.save(newOrder);
        log.info("OrderEntity Saved Successfully. ");
        orderEventService.saveOrderEvent(buildOrderEvent(newOrder, OrderEventType.ORDER_CREATED));
        log.info("OrderEvent Saved Successfully. ");
        return new CreateOrderResponse(orderEntity.getOrderNumber());
    }

    public void processOrder() {
        List<OrderEntity> orderEntityList = orderRepository.findByStatus(OrderStatus.NEW);
        orderEntityList.forEach(orderEntity -> {
            if (ValidCountryList.contains(
                    orderEntity.getDelivery().addressCountry().toUpperCase())) {
                orderEntity.setStatus(OrderStatus.DELIVERED);
                orderRepository.save(orderEntity);
                try {
                    orderEventService.saveOrderEvent(buildOrderEvent(orderEntity, OrderEventType.ORDER_DELIVERED));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                orderEntity.setStatus(OrderStatus.CANCELLED);
                orderRepository.save(orderEntity);
                try {
                    orderEventService.saveOrderEvent(buildOrderEvent(orderEntity, OrderEventType.ORDER_CANCELLED));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
