package com.snackhuborder.application.order.retrieve;

import com.snackhuborder.application.order.OrderOutput;
import com.snackhuborder.domain.order.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindOrdersByStatusUseCaseTest {

    private FindOrdersByStatusUseCase useCase;

    @Mock
    private OrderGateway orderGateway;

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
    void givenAValidOrders_whenCallUseCase_thenReturnOrder() {
        OrderItem orderItem = OrderItem.with(OrderItemId.from(1L), "Snack", BigDecimal.TEN, 2, OrderItemCategory.SNACK);
        final var expectedCustomerId = 11l;
        Order order = Order.with(OrderId.from(1L), Arrays.asList(orderItem), expectedCustomerId, "Maria", null, OrderStatus.RECEIVED, Instant.now());

        when(this.orderGateway.findOrdersByStatus(any(OrderStatus.class)))
                .thenReturn(Arrays.asList(order));

        List<OrderOutput> orderOutputs = new FindOrdersByStatusUseCase(this.orderGateway).execute(OrderStatus.RECEIVED);
        Assertions.assertNotNull(orderOutputs);
        Assertions.assertEquals(1, orderOutputs.size());
    }
}
