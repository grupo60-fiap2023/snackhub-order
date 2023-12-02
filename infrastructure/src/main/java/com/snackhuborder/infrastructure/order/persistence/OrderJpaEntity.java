package com.snackhuborder.infrastructure.order.persistence;

import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderId;
import com.snackhuborder.domain.order.OrderStatus;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Order")
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "orderJpaEntity", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private List<OrderItemJpaEntity> items;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "order_identifier")
    private String orderIdentifier;

    @Column(name = "observation")
    private String observation;

    @Column(name = "status", nullable = false)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    public OrderJpaEntity() {
    }

    public OrderJpaEntity(Long id,
                          List<OrderItemJpaEntity> items,
                          Long customerId,
                          String orderIdentifier,
                          String observation,
                          OrderStatus orderStatus,
                          Instant createdAt) {
        this.id = id;
        this.items = items;
        this.customerId = customerId;
        this.orderIdentifier = orderIdentifier;
        this.observation = observation;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public OrderJpaEntity(List<OrderItemJpaEntity> items,
                          Long customerId,
                          String orderIdentifier,
                          String observation,
                          OrderStatus orderStatus,
                          Instant createdAt) {
        this.items = items;
        this.customerId = customerId;
        this.orderIdentifier = orderIdentifier;
        this.observation = observation;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public static OrderJpaEntity create(final Order order) {
        OrderJpaEntity orderJpaEntity = new OrderJpaEntity(
                new ArrayList<>(),
                order.getCustomerId(),
                order.getOrderIdentifier(),
                order.getObservation(),
                order.getStatus(),
                order.getCreatedAt());

        order.getItems().stream().map(orderItem -> OrderItemJpaEntity.create(orderItem)).forEach(orderJpaEntity.getItems()::add);

        return orderJpaEntity;
    }

    public static OrderJpaEntity from(final Order order) {
        OrderJpaEntity orderJpaEntity = new OrderJpaEntity(
                order.getId().getValue(),
                new ArrayList<>(),
                order.getCustomerId(),
                order.getOrderIdentifier(),
                order.getObservation(),
                order.getStatus(),
                order.getCreatedAt());

        order.getItems().stream().map(orderItem -> OrderItemJpaEntity.from(orderItem)).forEach(orderJpaEntity.getItems()::add);

        return orderJpaEntity;
    }

    public static OrderJpaEntity from(final OrderStatus status) {
        return new OrderJpaEntity(
                null,
                null,
                null,
                null,
                status,
                null);
    }

    public Order toAggregate() {
        var orderItems = getItems().stream().map(OrderItemJpaEntity::toAggregate).toList();
        return Order.with(
                OrderId.from(getId()),
                orderItems,
                getCustomerId(),
                getOrderIdentifier(),
                getObservation(),
                getOrderStatus(),
                getCreatedAt());
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemJpaEntity> getItems() {
        return items;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public String getObservation() {
        return observation;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
