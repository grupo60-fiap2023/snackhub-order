package com.snackhuborder.domain.order;


import com.snackhuborder.domain.Entity;
import com.snackhuborder.domain.utils.InstantUtils;
import com.snackhuborder.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.List;

public class Order extends Entity<OrderId> {

    private final List<OrderItem> items;
    private final Long customerId;

    private final String orderIdentifier;
    private final String observation;
    private OrderStatus status;
    private final Instant createdAt;

    private Order(final OrderId orderId,
                  final List<OrderItem> items,
                  final Long customerId,
                  final String orderIdentifier,
                  final String observation,
                  final OrderStatus status,
                  final Instant creationDate) {
        super(orderId);
        this.items = items;
        this.customerId = customerId;
        this.orderIdentifier = orderIdentifier;
        this.observation = observation;
        this.status = status;
        this.createdAt = creationDate;
    }

    public static Order newOrder(final List<OrderItem> items, final Long customerId, final String orderIdentifier, final String observation) {
        final var now = InstantUtils.now();
        return new Order(null, items, customerId, orderIdentifier, observation, OrderStatus.RECEIVED, now);
    }

    public static Order with(final OrderId orderId,
                             final List<OrderItem> items,
                             final Long customerId,
                             final String orderIdentifier,
                             final String observation,
                             final OrderStatus status,
                             final Instant createdAt) {
        return new Order(orderId, items, customerId, orderIdentifier, observation, status, createdAt);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new OrderValidator(this, handler).validate();
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getObservation() {
        return observation;
    }

    public OrderStatus getStatus() {
        return status;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void changeStatus(OrderStatus newStatus){
        this.status = newStatus;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }
}
