package com.snackhuborder.infrastructure.order.models.queue;

import java.util.List;

public record OrderSuccessfulSchema(
        Long orderId,
        String orderIdentifier,
        List<OrderItemSchema> items
) {
}
