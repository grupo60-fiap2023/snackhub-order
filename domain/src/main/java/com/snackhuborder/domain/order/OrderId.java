package com.snackhuborder.domain.order;


import com.snackhuborder.domain.Identifier;

public class OrderId extends Identifier {

    private final Long value;

    public OrderId(Long value) {
        this.value = value;
    }

    public static OrderId from(final Long id) {
        return new OrderId(id);
    }

    @Override
    public Long getValue() {
        return value;
    }
}
