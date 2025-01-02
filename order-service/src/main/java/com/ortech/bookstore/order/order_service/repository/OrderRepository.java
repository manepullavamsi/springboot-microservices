package com.ortech.bookstore.order.order_service.repository;

import com.ortech.bookstore.order.order_service.domain.OrderEntity;
import com.ortech.bookstore.order.order_service.domain.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus string);
}
