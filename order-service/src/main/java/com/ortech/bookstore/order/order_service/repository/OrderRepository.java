package com.ortech.bookstore.order.order_service.repository;

import com.ortech.bookstore.order.order_service.domain.OrderEntity;
import com.ortech.bookstore.order.order_service.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
   List<OrderEntity> findByStatus(OrderStatus string);
}
