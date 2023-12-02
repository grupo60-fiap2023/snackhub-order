package com.snackhuborder.infrastructure.order.persistence;

import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.OrderMySQLGateway;
import com.snackhuborder.infrastructure.util.OrderHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class OrderMySQLGatewayIT {

    @Autowired
    private OrderMySQLGateway orderMySQLGateway;

    @Test
    void shouldSaveOrder() {
        var order = Order.newOrder(Arrays.asList(OrderHelper.getOrderItemMock()), 1L, "ORDER123","First order");
        // Act
        var savedOrder = orderMySQLGateway.save(order);
        // Assert
        assertThat(order)
                .isInstanceOf(Order.class)
                .isNotNull();
        assertThat(savedOrder.getCustomerId())
                .isEqualTo(order.getCustomerId());
        assertThat(savedOrder.getOrderIdentifier())
                .isEqualTo(order.getOrderIdentifier());
        assertThat(savedOrder.getStatus())
                .isEqualTo(order.getStatus());
        assertThat(savedOrder.getCreatedAt())
                .isEqualTo(order.getCreatedAt());
        assertThat(savedOrder.getObservation())
                .isEqualTo(order.getObservation());
    }

    @Test
    void shouldFindAllOrder() {
        var result = this.orderMySQLGateway.findAllOrders();
        assertThat(result)
                .hasSize(2);
    }

    @Test
    void shouldFindAllOrdersByStatus() {
        var order = OrderHelper.getOrderMock();
        var status = Set.of(order.getStatus());
        var result = this.orderMySQLGateway.findAllOrdersByStatus(status);
        assertThat(result)
                .hasSize(1);
        assertOrder(order, result.stream().findFirst());
    }

    @Test
    void shouldFindOrdersByStatus() {
        var result = this.orderMySQLGateway.findOrdersByStatus(OrderStatus.IN_PREPARATION);
        assertThat(result)
                .hasSize(1);

        var savedOrder = result.stream().findFirst().get();
        assertThat(savedOrder.getOrderIdentifier())
                .isEqualTo("ORDER345");
        assertThat(savedOrder.getObservation())
                .isEqualTo("Second order");
    }

    @Test
    void shouldUpdateOrder() {
        var order = OrderHelper.getOrderMock();
        order.changeStatus(OrderStatus.FINISHED);
        var result = this.orderMySQLGateway.update(order);
        assertOrder(order, Optional.of(result));
    }

    @Test
    void shouldFindOrdersById() {
        var order = OrderHelper.getOrderMock();
        var optionalOrder = this.orderMySQLGateway.findOrderById(order.getId());
        assertOrder(order, optionalOrder);
    }

    private void assertOrder(Order order, Optional<Order> optionalOrder) {
        optionalOrder.ifPresent(savedOrder -> {
            assertThat(savedOrder.getId().getValue())
                    .isEqualTo(order.getId().getValue());
            assertThat(savedOrder.getCustomerId())
                    .isEqualTo(order.getCustomerId());
            assertThat(savedOrder.getOrderIdentifier())
                    .isEqualTo(order.getOrderIdentifier());
            assertThat(savedOrder.getStatus())
                    .isEqualTo(order.getStatus());
            assertThat(savedOrder.getCreatedAt())
                    .isEqualTo(order.getCreatedAt());
            assertThat(savedOrder.getObservation())
                    .isEqualTo(order.getObservation());

            var savedItem = savedOrder.getItems().stream().findFirst().get();
            var item = OrderHelper.getOrderItemMock();
            assertThat(savedItem.getId().getValue())
                    .isEqualTo(item.getId().getValue());
            assertThat(savedItem.getName())
                    .isEqualTo(item.getName());
            assertThat(savedItem.getPrice())
                    .isEqualTo(item.getPrice());
            assertThat(savedItem.getCategory())
                    .isEqualTo(item.getCategory());
            assertThat(savedItem.getQuantity())
                    .isEqualTo(item.getQuantity());
        });
    }
}
