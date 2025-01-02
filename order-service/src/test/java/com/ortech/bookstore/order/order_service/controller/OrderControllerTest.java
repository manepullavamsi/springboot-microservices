package com.ortech.bookstore.order.order_service.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

import com.ortech.bookstore.order.order_service.AbstractIT;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTest extends AbstractIT {
    @Nested
    class CreateOrderTest {
        @Test
        void testCreateOrderSuccessful() {

            mockGetProductCode("P10", "Cocunut", new BigDecimal(10));
            var payload =
                    """
                      {
                      "items": [
                        {
                          "code": "P10",
                          "name": "Cocunut",
                          "price": 10,
                          "quantity": 1
                        }
                      ],
                      "customer": {
                        "name": "TTD",
                        "email": "TAHgs@gmai.com",
                        "phoneNumber": "1234678901"
                      },
                      "deliveryDetails": {
                        "addressLine1": "sw2",
                        "addressLine2": "sw3",
                        "addressCity": "Banglore",
                        "addressState": "Karnataka",
                        "addressZipCode": "51711",
                        "addressCountry": "India"
                      }
                    }
                                    """;

            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderId", notNullValue());
        }

        @Test
        void testCreateOrderBadRequest() {
            var payload =
                    """
                      {
                      "items": [
                        {
                          "code": "SVC",
                          "name": "Cocunut",
                          "price": 123,
                          "quantity": 1
                        }
                      ],
                      "customer": {
                        "name": "TTD",
                        "email": "TAHgs@gmai.com",
                        "phoneNumber": "1234678901"
                      },
                      "deliveryDetails": {
                        "addressLine1": "sw2",
                        "addressLine2": "sw3",
                        "addressCity": "",
                        "addressState": "Karnataka",
                        "addressZipCode": "51711",
                        "addressCountry": "India"
                      }
                    }
                                    """;

            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
