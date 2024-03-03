package com.snackhuborder.infrastructure.order;

import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderGateway;
import com.snackhuborder.domain.order.OrderId;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.persistence.OrderJpaEntity;
import com.snackhuborder.infrastructure.order.persistence.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;



import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class OrderMySQLGateway implements OrderGateway {

    private final OrderRepository repository;

    public OrderMySQLGateway(final OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        return this.repository.save(OrderJpaEntity.create(order)).toAggregate();
    }

    @Override
    @Transactional
    public List<Order> findAllOrders() {
        return this.repository.findAll().stream().map(OrderJpaEntity::toAggregate).toList();
    }

    @Override
    @Transactional
    public List<Order> findAllOrdersByStatus(Set<OrderStatus> status) {
        return this.repository.findAllOrderByStatusAndCreatedDate(status).stream().map(OrderJpaEntity::toAggregate).toList();
    }

    @Override
    @Transactional
    public List<Order> findOrdersByStatus(OrderStatus status) {
        OrderJpaEntity orderJpaEntity = OrderJpaEntity.from(status);
        Example<OrderJpaEntity> example = Example.of(orderJpaEntity);
        return this.repository.findAll(example).stream().map(OrderJpaEntity::toAggregate).toList();
    }

    @Override
    public Order update(Order order) {
        return this.repository.save(OrderJpaEntity.from(order)).toAggregate();
    }

    @Override
    @Transactional
    public Optional<Order> findOrderById(OrderId orderId) {
        return this.repository.findById(orderId.getValue()).map(OrderJpaEntity::toAggregate);
    }
}
