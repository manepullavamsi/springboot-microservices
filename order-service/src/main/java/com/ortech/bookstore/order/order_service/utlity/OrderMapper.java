package com.ortech.bookstore.order.order_service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
@UtilityClass
public class OrderMapper {


    public static OrderEntity createOrderRequestMapper(CreateOrderRequest request) {
        OrderEntity orderEntity = new OrderEntity();
        Set<OrderItemEntity> orderItemEntities = new HashSet<>();
        for (var order : request.items()) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setCode(order.code());
            orderItemEntity.setName(order.name());
            orderItemEntity.setPrice(order.price());
            orderItemEntity.setQuantity(order.quantity());
            orderItemEntity.setOrders(orderEntity);
            orderItemEntities.add(orderItemEntity);
        }
        orderEntity.setOrderItemEntities(orderItemEntities);
        orderEntity.setOrderNumber(UUID.randomUUID().toString());
        orderEntity.setDelivery(request.deliveryDetails());
        orderEntity.setCustomerDetails(request.customer());
        orderEntity.setStatus(OrderStatus.NEW);
        return orderEntity;
    }

    public static OrderEvent buildOrderEvent(OrderEntity orderEntity, OrderEventType orderEventType) {
        return new OrderEvent(UUID.randomUUID().toString(), orderEntity.getOrderNumber(), orderEventType,
                getOrderItems(orderEntity.getOrderItemEntities()), orderEntity.getCustomerDetails(),
                orderEntity.getDelivery(), LocalDateTime.now());
    }


    public static Set<OrderItem> getOrderItems(Set<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream().map(i -> new OrderItem(i.getCode(), i.getName(), i.getPrice(), i.getQuantity()))
                .collect(Collectors.toSet());
    }

    public static <T> T fromJsonPayload(String payload, Class<T> type) {
       ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(payload, type);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
