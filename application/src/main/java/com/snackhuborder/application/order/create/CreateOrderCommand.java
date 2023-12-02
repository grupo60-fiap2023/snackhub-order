package com.snackhuborder.application.order.create;

import java.util.List;

public record CreateOrderCommand(List<CreateOrderItemCommand> items,
                                 Long customerId,
                                 String orderIdentifier,
                                 String observation) {

    public static CreateOrderCommand with(
            final List<CreateOrderItemCommand> items,
            final Long customerId,
            final String orderIdentifier,
            final String observation
    ) {
        return new CreateOrderCommand(items, customerId, orderIdentifier, observation);
    }
}
