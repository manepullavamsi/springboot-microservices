package com.ortech.bookstore.catalog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq_generator")
    @SequenceGenerator(name = "product_id_seq_generator", sequenceName = "product_id_seq")
    Long id;

    @NotEmpty(message = "Code can't be null")
    @Column(nullable = false, unique = true)
    String code;

    @NotEmpty(message = "Name can't be Empty")
    @Column(nullable = false)
    String name;

    String description;

    String image_url;

    @NotNull(message = "price can't be Null") @DecimalMin("0.1")
    @Column(nullable = false)
    BigDecimal price;
}
