package com.snackhuborder.infrastructure.order.adapter;

import com.snackhuborder.domain.order.OrderItem;
import com.snackhuborder.infrastructure.order.models.queue.OrderItemSchema;
import com.snackhuborder.infrastructure.order.models.queue.OrderSuccessfulSchema;

public class OrderItemAdapter {

    static OrderItemSchema orderItemToSchema(OrderItem item) {
        return new OrderItemSchema(
                item.getName(),
                item.getQuantity(),
                item.getCategory().name());
    }
}
