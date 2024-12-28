package com.ortech.bookstore.order.order_service.service;

import com.ortech.bookstore.order.order_service.domain.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductServiceClient {

    private final RestClient catalogRestClient;

    @CircuitBreaker(name = "catalog-service")
    @Retry(name="catalog-service",fallbackMethod = "handleBackOffMethod")
    public Optional<Product> getProductByCode(String code)
    {
        log.info("getProductByCode for code : {}",code);

            var product= catalogRestClient.get().uri("/api/products/{code}",code)
                    .retrieve()
                    .body(Product.class);
            return Optional.ofNullable(product);

    }

    Optional<Product> handleBackOffMethod(String code,Throwable ex)
    {
        log.error("handleBackOffMethod : {} ",code);
        return Optional.empty();
    }


}
