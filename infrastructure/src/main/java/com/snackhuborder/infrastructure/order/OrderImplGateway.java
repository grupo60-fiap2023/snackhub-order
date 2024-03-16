package com.snackhuborder.infrastructure.order;

import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderGateway;
import com.snackhuborder.domain.order.OrderId;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.adapter.OrderAdapter;
import com.snackhuborder.infrastructure.order.persistence.OrderJpaEntity;
import com.snackhuborder.infrastructure.order.persistence.OrderRepository;

import com.snackhuborder.infrastructure.queue.producer.OrderProducer;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class OrderImplGateway implements OrderGateway {

    private final OrderRepository repository;

    private final OrderProducer producer;

    public OrderImplGateway(final OrderRepository repository, OrderProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Override
    @Transactional
    public Order create(Order order) throws Exception {
        Order orderSaved = this.repository.save(OrderJpaEntity.create(order)).toAggregate();
        this.producer.sendOrder(OrderAdapter.toSchema(orderSaved));
        return orderSaved;
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
    public Order update(Order order) throws Exception {
        Order orderUpdated = this.repository.save(OrderJpaEntity.from(order)).toAggregate();
        if(orderUpdated.getStatus().isPaymentAccept()){
            this.producer.sendOrderSuccessful(OrderAdapter.toOrderSuccessful(orderUpdated));
        }
        return orderUpdated;
    }

    @Override
    @Transactional
    public Optional<Order> findOrderById(OrderId orderId) {
        return this.repository.findById(orderId.getValue()).map(OrderJpaEntity::toAggregate);
    }
}
