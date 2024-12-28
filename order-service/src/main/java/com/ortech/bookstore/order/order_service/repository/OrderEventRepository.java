package com.ortech.bookstore.order.order_service.repository;

import com.ortech.bookstore.order.order_service.domain.OrderEventsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEventRepository extends JpaRepository<OrderEventsEntity,Long> {
}
