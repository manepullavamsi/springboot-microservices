package com.ortech.bookstore.order.order_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_seq_generator")
    @SequenceGenerator(name = "order_item_id_seq_generator", sequenceName = "order_item_id_seq")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    @Min(1)
    private BigDecimal price;

    @Column(name = "quantity")
    @Min(1)
    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity orders;
}
