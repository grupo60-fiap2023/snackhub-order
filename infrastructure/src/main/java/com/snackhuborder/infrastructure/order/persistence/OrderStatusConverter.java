package com.snackhuborder.infrastructure.order.persistence;

import com.snackhuborder.domain.order.OrderStatus;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus.getId();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer id) {
        return Stream.of(OrderStatus.values()).filter(
                orderStatus -> orderStatus.getId().equals(id))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
