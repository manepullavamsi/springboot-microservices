package com.ortech.bookstore.order.order_service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class AbstractIT {
    @LocalServerPort
    int port;

 static WireMockContainer wireMockContainer=new WireMockContainer("wiremock/wiremock:3.5.2-alpine");
    @BeforeAll
 static void beforeAll()
 {
     wireMockContainer.start();
     configureFor(wireMockContainer.getHost(), wireMockContainer.getPort());
 }
@DynamicPropertySource
 static void configureProperties(DynamicPropertyRegistry registry)
 {
     registry.add("orders.catalog-service-url",wireMockContainer::getBaseUrl);
 }

 protected static void mockGetProductCode(String code, String name, BigDecimal price)
 {
     stubFor(WireMock.get("/api/products/"+code).willReturn(aResponse()
             .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
             .withStatus(200)
             .withBody( """
                    {
                        "code": "%s",
                        "name": "%s",
                        "price": %f
                    }
                """.formatted(code, name, price.doubleValue()))));



 }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}
