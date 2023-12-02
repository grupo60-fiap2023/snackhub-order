package com.snackhuborder.application.order.create;

import com.snackhuborder.domain.order.OrderItemCategory;

import java.math.BigDecimal;

public record CreateOrderItemCommand(String name, BigDecimal price, Integer quantity, OrderItemCategory category) {

    public static CreateOrderItemCommand with(
            final String name,
            final BigDecimal price,
            final Integer quantity,
            final OrderItemCategory category
    ) {
        return new CreateOrderItemCommand(name, price, quantity, category);
    }
}
