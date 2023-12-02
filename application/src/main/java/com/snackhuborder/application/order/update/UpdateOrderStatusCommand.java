package com.snackhuborder.application.order.update;

import com.snackhuborder.domain.order.OrderStatus;

public record UpdateOrderStatusCommand(Long orderId,
                                       OrderStatus status) {

    public static UpdateOrderStatusCommand with(
            final Long orderId,
            final OrderStatus status
    ) {
        return new UpdateOrderStatusCommand(orderId, status);
    }
}
