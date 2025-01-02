package com.ortech.bookstore.catalog.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.ortech.bookstore.catalog.ContainersConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(ContainersConfig.class)
@Sql("/raw-data.sql")
public class ProductControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetAllProducts() {
        given().contentType(ContentType.JSON)
                .when()
                .param("pageNumber", 1)
                .get("/api/products/all")
                .then()
                .statusCode(200)
                .body("data", hasSize(5))
                .body("totalElements", is(14));
    }

    @Test
    void testGetByCode() {
        given().contentType(ContentType.JSON)
                .when()
                .pathParam("code", "P100")
                .get("api/products/{code}")
                .then()
                .statusCode(200)
                .body("code", equalTo("P100"));
    }

    @Test
    void testGetByCodeException() {
        given().contentType(ContentType.JSON)
                .when()
                .pathParam("code", "P1001")
                .get("api/products/{code}")
                .then()
                .statusCode(404)
                .body("service", equalTo("catalog-service"));
    }
}
