package com.snackhuborder.application.order.retrieve;

import com.snackhuborder.application.NullaryUseCase;
import com.snackhuborder.application.order.OrderOutput;
import com.snackhuborder.domain.order.OrderGateway;
import com.snackhuborder.domain.order.OrderStatus;

import java.util.*;

public class FindAllOrdersUseCase extends NullaryUseCase<List<OrderOutput>> {

    private final OrderGateway orderGateway;

    public FindAllOrdersUseCase(final OrderGateway orderGateway) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Override
    public List<OrderOutput> execute() {
        Set<OrderStatus> statusList = new HashSet<>(Arrays.asList(OrderStatus.FINISHED, OrderStatus.IN_PREPARATION, OrderStatus.RECEIVED));
        return this.orderGateway.findAllOrdersByStatus(statusList).stream().map(OrderOutput::from).toList();
    }
}
