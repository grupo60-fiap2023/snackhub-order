package com.snackhuborder.domain.order;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderGateway {

    Order create(Order order) throws Exception;

    List<Order> findAllOrders();

    List<Order> findOrdersByStatus(OrderStatus status);

    Order update(Order order) throws Exception;

    Optional<Order> findOrderById(OrderId orderId);

    List<Order> findAllOrdersByStatus(Set<OrderStatus> status);
}
