package com.snackhuborder.infrastructure.order.persistence;

import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderItem;
import com.snackhuborder.domain.order.OrderItemCategory;
import com.snackhuborder.domain.order.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldSaveOrder() {
        var orderJpaEntity = getOrderMock();
        when(this.orderRepository.save(any(OrderJpaEntity.class))).thenReturn(orderJpaEntity);

        var savedOrder = this.orderRepository.save(orderJpaEntity);

        verify(this.orderRepository, times(1)).save(orderJpaEntity);
        assertThat(savedOrder)
                .isInstanceOf(OrderJpaEntity.class)
                .isNotNull()
                .isEqualTo(orderJpaEntity);
        assertThat(savedOrder)
                .extracting(OrderJpaEntity::getId)
                .isEqualTo(orderJpaEntity.getId());
        assertThat(savedOrder)
                .extracting(OrderJpaEntity::getCustomerId)
                .isEqualTo(orderJpaEntity.getCustomerId());
        assertThat(savedOrder)
                .extracting(OrderJpaEntity::getOrderIdentifier)
                .isEqualTo(orderJpaEntity.getOrderIdentifier());
        assertThat(savedOrder)
                .extracting(OrderJpaEntity::getOrderStatus)
                .isEqualTo(orderJpaEntity.getOrderStatus());
        assertThat(savedOrder)
                .extracting(OrderJpaEntity::getCreatedAt)
                .isEqualTo(orderJpaEntity.getCreatedAt());
        assertThat(savedOrder)
                .extracting(OrderJpaEntity::getObservation)
                .isEqualTo(orderJpaEntity.getObservation());
        assertThat(savedOrder)
                .extracting(OrderJpaEntity::getItems)
                .isEqualTo(orderJpaEntity.getItems());
    }

    @Test
    void shouldFindAllOrder() {
        var order1 = getOrderMock();
        var order2 = getOrderMock();
        var orders = Arrays.asList(order1, order2);

        when(this.orderRepository.findAll()).thenReturn(orders);
        var result = this.orderRepository.findAll();

        verify(this.orderRepository, times(1)).findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(order1, order2);
    }

    @Test
    void shouldFindAllOrderByStatusAndCreatedDate() {
        var order1 = getOrderMock();
        var order2 = getOrderMock();
        var orders = Arrays.asList(order1, order2);
        var status = Set.of(OrderStatus.RECEIVED);

        when(this.orderRepository.findAllOrderByStatusAndCreatedDate(status)).thenReturn(orders);
        var result = this.orderRepository.findAllOrderByStatusAndCreatedDate(status);

        verify(this.orderRepository, times(1)).findAllOrderByStatusAndCreatedDate(status);
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(order1, order2);
    }

    private OrderJpaEntity getOrderMock() {
        Order order = Order.newOrder(Arrays.asList(getOrderItemMock()), 1L, "Maria",null);
        return OrderJpaEntity.create(order);
    }

    private OrderItem getOrderItemMock(){
        final var expectedName = "Big Mac";
        final var expectedPrice = BigDecimal.valueOf(25.0);
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = 2;

        return OrderItem.newOrderItem(expectedName, expectedPrice, expectedQuantity, expectedCategory);
    }

}
