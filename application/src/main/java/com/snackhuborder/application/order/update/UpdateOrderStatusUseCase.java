package com.snackhuborder.application.order.update;

import com.snackhuborder.application.UseCase;
import com.snackhuborder.application.order.OrderOutput;
import com.snackhuborder.domain.exceptions.DomainException;
import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderGateway;
import com.snackhuborder.domain.order.OrderId;
import com.snackhuborder.domain.validation.Error;

import java.util.Objects;
import java.util.Optional;

public class UpdateOrderStatusUseCase extends UseCase<UpdateOrderStatusCommand, OrderOutput> {

    private final OrderGateway orderGateway;

    public UpdateOrderStatusUseCase(final OrderGateway orderGateway) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Override
    public OrderOutput execute(UpdateOrderStatusCommand command) {
        Optional<Order> optionalOrder = this.orderGateway.findOrderById(OrderId.from(command.orderId()));
        if(optionalOrder.isEmpty()){
            throw DomainException.with(new Error("Ordem n\u00E3o existe"));
        }

        Order order = optionalOrder.get();
        order.changeStatus(command.status());
        return OrderOutput.from(this.orderGateway.update(order));
    }
}
