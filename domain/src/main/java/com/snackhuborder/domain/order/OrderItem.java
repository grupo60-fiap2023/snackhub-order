package com.snackhuborder.domain.order;


import com.snackhuborder.domain.Entity;
import com.snackhuborder.domain.validation.ValidationHandler;

import java.math.BigDecimal;

public class OrderItem  extends Entity<OrderItemId> {

    private final String name;
    private final BigDecimal price;
    private final OrderItemCategory category;
    private final Integer quantity;

    private OrderItem(final OrderItemId itemId, final String name, final BigDecimal price, final Integer quantity, final OrderItemCategory category) {
        super(itemId);
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public static OrderItem newOrderItem(final String name, final BigDecimal price, final Integer quantity, final OrderItemCategory category) {
        return new OrderItem(null, name, price, quantity, category);
    }

    public static OrderItem with(final OrderItemId orderItemId, final String name, final BigDecimal price, final Integer quantity, final OrderItemCategory category) {
        return new OrderItem(orderItemId, name, price, quantity, category);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new OrderItemValidator(this, handler).validate();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderItemCategory getCategory() {
        return category;
    }
}
