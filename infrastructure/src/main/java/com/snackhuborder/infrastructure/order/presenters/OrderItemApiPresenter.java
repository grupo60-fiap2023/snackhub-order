package com.snackhuborder.infrastructure.order.presenters;

import com.snackhuborder.application.order.OrderItemOutput;
import com.snackhuborder.application.order.create.CreateOrderItemCommand;
import com.snackhuborder.infrastructure.order.models.OrderItemRequest;
import com.snackhuborder.infrastructure.order.models.OrderItemResponse;

import java.util.List;
import java.util.Objects;

public interface OrderItemApiPresenter {

    static List<CreateOrderItemCommand> present(List<OrderItemRequest> itemsRequest) {
        if(Objects.isNull(itemsRequest)){
            return null;
        }

        return itemsRequest.stream().map(
                itemRequest -> CreateOrderItemCommand.with(itemRequest.name(),
                        itemRequest.price(),
                        itemRequest.quantity(),
                        itemRequest.category()))
                .toList();
    }

    static OrderItemResponse present(final OrderItemOutput orderItem) {
        return new OrderItemResponse(orderItem.id(),
                orderItem.name(),
                orderItem.price(),
                orderItem.quantity(),
                orderItem.category());
    }
}
