package com.snackhuborder.application.order;

import com.snackhuborder.domain.order.OrderItem;
import com.snackhuborder.domain.order.OrderItemCategory;

import java.math.BigDecimal;

public record OrderItemOutput(Long id,
                              String name,
                              BigDecimal price,
                              OrderItemCategory category,
                              Integer quantity) {

    public static OrderItemOutput from(final OrderItem orderItem) {
        Long orderItemId = null;
        if(orderItem.getId() != null){
            orderItemId = orderItem.getId().getValue();
        }

        return new OrderItemOutput(orderItemId,
                orderItem.getName(), orderItem.getPrice(),  orderItem.getCategory(), orderItem.getQuantity());
    }
}
