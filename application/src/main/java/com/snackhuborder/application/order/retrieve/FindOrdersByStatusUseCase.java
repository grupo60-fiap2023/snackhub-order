package com.snackhuborder.application.order.retrieve;

import com.snackhuborder.application.UseCase;
import com.snackhuborder.application.order.OrderOutput;
import com.snackhuborder.domain.order.OrderGateway;
import com.snackhuborder.domain.order.OrderStatus;

import java.util.List;
import java.util.Objects;

public class FindOrdersByStatusUseCase extends UseCase<OrderStatus, List<OrderOutput>> {

    private final OrderGateway orderGateway;

    public FindOrdersByStatusUseCase(final OrderGateway orderGateway) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Override
    public List<OrderOutput> execute(OrderStatus status) {
        return this.orderGateway.findOrdersByStatus(status).stream().map(OrderOutput::from).toList();
    }
}
