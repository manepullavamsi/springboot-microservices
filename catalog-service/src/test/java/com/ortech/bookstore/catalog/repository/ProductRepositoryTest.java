package com.ortech.bookstore.catalog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ortech.bookstore.catalog.domain.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest(
        properties = {"spring.test.database.replace=none", "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"})
@Sql("/raw-data.sql")
// @Import(ContainersConfig.class) //To avoid In case if we have multiple config just avoid unnecessary dependent to
// start we would be using test containers so only postgres would be bringing up
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindAll() {
        var res = productRepository.findAll();
        assertThat(res).hasSize(14);
    }

    @Test
    void testFindByCode() {
        var res = productRepository.findByCode("P100").orElse(new ProductEntity());
        assertThat(res.getCode()).isEqualTo("P100");
    }

    @Test
    void testFindByCodeNotFound() {
        var res = productRepository.findByCode("P1001");
        assertThat(res.isEmpty()).isTrue();
    }
}
