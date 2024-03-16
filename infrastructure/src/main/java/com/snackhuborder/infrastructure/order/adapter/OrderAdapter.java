package com.snackhuborder.infrastructure.order.adapter;

import com.snackhuborder.domain.order.Order;
import com.snackhuborder.infrastructure.order.models.queue.OrderSuccessfulSchema;
import com.snackhuborder.infrastructure.order.models.queue.OrderSchema;

public class OrderAdapter {

    public static OrderSchema toSchema (Order order) {
        return new OrderSchema(
                order.getId().getValue(),
                order.getCustomerId(),
                order.getTotal(),
                order.getOrderIdentifier());
    }

    public static OrderSuccessfulSchema toOrderSuccessful(Order order) {
        var items = order.getItems().stream().map(OrderItemAdapter::orderItemToSchema).toList();
        return new OrderSuccessfulSchema(
                order.getId().getValue(),
                order.getOrderIdentifier(),
                items);
    }
}
