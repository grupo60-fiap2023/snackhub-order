package com.snackhuborder.infrastructure.order.persistence;

import com.snackhuborder.domain.order.OrderItemCategory;

import jakarta.persistence.AttributeConverter;
import java.util.stream.Stream;

public class OrderItemCategoryConverter implements AttributeConverter<OrderItemCategory, String> {
    @Override
    public String convertToDatabaseColumn(OrderItemCategory category) {
        return category.getName();
    }

    @Override
    public OrderItemCategory convertToEntityAttribute(String value) {
        return Stream.of(OrderItemCategory.values()).filter(category -> category.getName().equalsIgnoreCase(value)).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
