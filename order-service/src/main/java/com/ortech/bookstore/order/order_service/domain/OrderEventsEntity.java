package com.ortech.bookstore.order.order_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_events")
@Data
public class OrderEventsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_items_id_generator")
    @SequenceGenerator(name = "order_items_id_generator",sequenceName = "order_event_id_seq")
    private Long id;

    @NotBlank
    @Column(name = "order_number")
    private String orderNumber;
    @NotBlank
    @Column(name = "event_id")
    private String eventId;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private OrderEventType eventType;
    @NotBlank
    @Column(name = "payload")
    private String payload;

    @Column(updatable = false,name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name ="updated_at" )
    private LocalDateTime updatedAt;

}
