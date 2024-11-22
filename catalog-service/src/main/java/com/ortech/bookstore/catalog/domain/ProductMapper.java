package com.ortech.bookstore.catalog.domain;

public class ProductMapper {
    public static Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getCode(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getImage_url(),
                productEntity.getPrice());
    }
}
