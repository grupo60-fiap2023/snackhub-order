package com.snackhuborder.infrastructure.order.models.queue;

import java.util.List;

public record OrderSchema (
        Long orderId,
        String orderIdentifier,
        List<OrderItemSchema> items
) {
}
