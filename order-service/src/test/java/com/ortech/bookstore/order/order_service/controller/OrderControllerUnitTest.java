package com.ortech.bookstore.order.order_service.controller;

import static com.ortech.bookstore.order.order_service.testdata.TestDataFactory.*;
import static com.ortech.bookstore.order.order_service.testdata.TestDataFactory.createValidOrderRequest;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ortech.bookstore.order.order_service.config.ApplicationProperties;
import com.ortech.bookstore.order.order_service.domain.CreateOrderRequest;
import com.ortech.bookstore.order.order_service.service.OrderService;
import com.ortech.bookstore.order.order_service.service.SecurityService;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
public class OrderControllerUnitTest {
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private OrderService orderService;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private ApplicationProperties applicationProperties;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest(name = "[{index}]- {0}")
    @MethodSource("createOrderRequestProvider")
    void testCreateOrderBadRequest(CreateOrderRequest createOrderRequest) throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOrderRequestCreated() throws Exception {

        when(securityService.getLoginUserName()).thenReturn("UserName");
        when(orderService.createOrder("UserName", createValidOrderRequest())).thenReturn(createOrderResponse());

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createValidOrderRequest())))
                .andExpect(status().isCreated());
    }

    public static Stream<Arguments> createOrderRequestProvider() {
        return Stream.of(
                arguments(named("Order with Invalid Customer", createOrderRequestWithInvalidCustomer())),
                arguments(named("Order With Invalid Address", createOrderRequestInvalidDeliveryAddress())),
                arguments(named("Order with No order Items", createOrderRequestWithNoItems())));
    }
}
