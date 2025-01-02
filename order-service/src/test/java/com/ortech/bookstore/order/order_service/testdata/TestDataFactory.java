package com.ortech.bookstore.order.order_service.testdata;

import static org.instancio.Select.field;

import com.ortech.bookstore.order.order_service.domain.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.instancio.Instancio;

public class TestDataFactory {
    public static final List<String> VALID_COUNTRIES = List.of("INDIA", "USA", "PAKISTAN");

    public static final Set<OrderItem> VALID_ORDER_ITEMS =
            Set.of(new OrderItem("P10", "Product1", new BigDecimal(10), 1));

    public static final Set<OrderItem> INVALID_ORDER_ITEMS = Set.of(new OrderItem("P1001", "", new BigDecimal(1), 0));

    public static CreateOrderRequest createValidOrderRequest() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#a#a#a#a#a#a#a@email.com"))
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .set(field(Customer::phoneNumber), "98209029812")
                .generate(field(DeliveryDetails::addressCountry), gen -> gen.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithInvalidCustomer() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c@gmail.com"))
                .set(field(Customer::name), "")
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .generate(field(DeliveryDetails::addressCountry), gen -> gen.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static CreateOrderRequest createOrderRequestInvalidDeliveryAddress() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#c@gmail.com"))
                .set(field(DeliveryDetails::addressCountry), "")
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .generate(field(DeliveryDetails::addressCountry), gen -> gen.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithNoItems() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#c#c#c@gmail.com"))
                .set(field(CreateOrderRequest::items), Set.of())
                .create();
    }

    public static CreateOrderResponse createOrderResponse() {
        return new CreateOrderResponse("12345");
    }
}
