package com.ortech.bookstore.order.order_service.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq_generator")
    @SequenceGenerator(name = "order_id_seq_generator", sequenceName = "order_id_seq")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "username")
    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orders", fetch = FetchType.EAGER)
    private Set<OrderItemEntity> orderItemEntities;

    @Embedded
    @AttributeOverrides(
            value = {
                @AttributeOverride(name = "name", column = @Column(name = "customer_name")),
                @AttributeOverride(name = "email", column = @Column(name = "customer_email")),
                @AttributeOverride(name = "phoneNumber", column = @Column(name = "customer_phone"))
            })
    private Customer customerDetails;

    @Embedded
    @AttributeOverrides(
            value = {
                @AttributeOverride(name = "addressLine1", column = @Column(name = "delivery_address_line1")),
                @AttributeOverride(name = "addressLine2", column = @Column(name = "delivery_address_line2")),
                @AttributeOverride(name = "addressCity", column = @Column(name = "delivery_address_city")),
                @AttributeOverride(name = "addressState", column = @Column(name = "delivery_address_state")),
                @AttributeOverride(name = "addressZipCode", column = @Column(name = "delivery_address_zip_code")),
                @AttributeOverride(name = "addressCountry", column = @Column(name = "delivery_address_country"))
            })
    private DeliveryDetails delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(name = "updated_at", updatable = false, nullable = false)
    private LocalDate updated_at;
}
