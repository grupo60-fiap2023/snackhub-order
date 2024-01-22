package com.snackhuborder.infrastructure.order.presenters;

import com.snackhuborder.application.order.OrderOutput;
import com.snackhuborder.infrastructure.order.models.OrderResponse;

public interface OrderApiPresenter {
    static OrderResponse present(OrderOutput order) {
        var items = order.items().stream().map(OrderItemApiPresenter::present).toList();
        return new OrderResponse(order.id(), items, order.orderIdentifier(), order.observation(), order.status().getName(), null);
    }
}
