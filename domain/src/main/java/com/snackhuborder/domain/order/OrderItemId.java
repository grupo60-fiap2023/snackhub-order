package com.snackhuborder.domain.order;


import com.snackhuborder.domain.Identifier;

public class OrderItemId extends Identifier {

    private final Long value;

    public OrderItemId(Long value) {
        this.value = value;
    }

    public static OrderItemId from(final Long id) {
        return new OrderItemId(id);
    }

    @Override
    public Long getValue() {
        return value;
    }
}
