package com.snackhuborder.infrastructure.order.models.queue;

import java.math.BigDecimal;

public record OrderSuccessfulSchema (
        Long orderId,
        Long customerId,
        BigDecimal value,
        String orderIdentifier) {
}
