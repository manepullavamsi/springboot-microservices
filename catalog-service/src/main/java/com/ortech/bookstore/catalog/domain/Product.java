package com.ortech.bookstore.catalog.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    String code;
    String name;
    String description;
    String image_url;
    BigDecimal price;
}
