package com.snackhuborder.infrastructure.util;

import com.snackhuborder.domain.order.*;
import com.snackhuborder.infrastructure.order.models.CreateOrderRequest;
import com.snackhuborder.infrastructure.order.models.OrderItemRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public abstract class OrderHelper {

    public static Order getOrderMock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse("2023-01-01 15:00:00.000", formatter);
        return Order.with(OrderId.from(1L), Arrays.asList(getOrderItemMock()), 1L, "ORDER123","First order", OrderStatus.RECEIVED, localDateTime.toInstant(ZoneOffset.UTC));
    }

    public static OrderItem getOrderItemMock(){
        final var expectedName = "Product A";
        final var expectedPrice = BigDecimal.valueOf(15.99);
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = 2;

        return OrderItem.with(OrderItemId.from(1L), expectedName, expectedPrice, expectedQuantity, expectedCategory);
    }

    public static CreateOrderRequest getOrderRequest() {
        OrderItemRequest itemRequest = new OrderItemRequest("name", BigDecimal.TEN, 2, OrderItemCategory.ACCOMPANIMENT);
        return new CreateOrderRequest(1L, Arrays.asList(itemRequest), "PEDIDO", null);
    }

    public static CreateOrderRequest getOrderRequestWithoutItems() {
        return new CreateOrderRequest(1L, null, "PEDIDO", null);
    }

    public static CreateOrderRequest getOrderRequestWithouMandadoryItemField() {
        OrderItemRequest itemRequest = new OrderItemRequest("name", null, 2, OrderItemCategory.ACCOMPANIMENT);
        return new CreateOrderRequest(1L, Arrays.asList(itemRequest), "PEDIDO", null);
    }
}
