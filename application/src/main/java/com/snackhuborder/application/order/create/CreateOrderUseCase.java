package com.snackhuborder.application.order.create;

import com.snackhuborder.application.UseCase;
import com.snackhuborder.application.order.OrderOutput;
import com.snackhuborder.domain.exceptions.DomainException;
import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderGateway;
import com.snackhuborder.domain.order.OrderItem;
import com.snackhuborder.domain.validation.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateOrderUseCase extends UseCase<CreateOrderCommand, OrderOutput> {

    private final OrderGateway orderGateway;

    public CreateOrderUseCase(final OrderGateway orderGateway) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Override
    public OrderOutput execute(CreateOrderCommand command) throws Exception {
        final var notification = Notification.create();

        Order order = Order.newOrder(getOrderItemsValid(command, notification), command.customerId(), command.orderIdentifier(), command.observation());

        order.validate(notification);
        if(notification.hasError()){
            throw DomainException.with(notification.getErrors());
        }
        return OrderOutput.from(this.orderGateway.create(order));
    }

    private List<OrderItem> getOrderItemsValid(CreateOrderCommand command, Notification notification) {
        List<CreateOrderItemCommand> items = command.items();
        if(Objects.isNull(items)){
            return new ArrayList<>();
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderItemCommand item: items) {
            OrderItem orderItem = OrderItem.newOrderItem(item.name(),
                    item.price(),
                    item.quantity(),
                    item.category());

            orderItem.validate(notification);

            if(notification.hasError()){
                throw DomainException.with(notification.getErrors());
            }
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
