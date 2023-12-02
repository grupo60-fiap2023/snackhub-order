package com.snackhuborder.application.order;

import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderStatus;

import java.time.Instant;
import java.util.List;

public record OrderOutput(Long id,
                          List<OrderItemOutput> items,
                          String observation,
                          String orderIdentifier,
                          OrderStatus status,
                          Instant creationDate) {

    public static OrderOutput from(final Order order) {
        List<OrderItemOutput> items = order.getItems().stream().map(OrderItemOutput::from).toList();

        Long orderId = null;
        if(order.getId() != null){
            orderId = order.getId().getValue();
        }
        return new OrderOutput(orderId, items, order.getObservation(), order.getOrderIdentifier(), order.getStatus(), order.getCreatedAt());
    }
}
