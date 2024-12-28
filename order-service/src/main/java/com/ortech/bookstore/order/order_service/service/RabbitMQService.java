package com.ortech.bookstore.order.order_service.service;

import com.ortech.bookstore.order.order_service.domain.MyPayload;

//@Service
public class RabbitMQService {

//    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void newOrdersListener(MyPayload myPayload) {
        System.out.println("My New Order : " + myPayload.content());
    }

//    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void newOrdersListener2(MyPayload myPayload) {
        System.out.println("My New Order from 2 : " + myPayload.content());
    }

//    @RabbitListener(queues = "${orders.delivered-orders-queue}")
    public void deliveryOrdersListener(MyPayload myPayload) {
        System.out.println("My Delivered Ordered : " + myPayload.content());
    }
}
