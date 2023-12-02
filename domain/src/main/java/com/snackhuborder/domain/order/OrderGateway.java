package com.snackhuborder.domain.order;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderGateway {

    Order save(Order order);

    List<Order> findAllOrders();

    List<Order> findOrdersByStatus(OrderStatus status);

    Order update(Order order);

    Optional<Order> findOrderById(OrderId orderId);

    List<Order> findAllOrdersByStatus(Set<OrderStatus> status);
}
