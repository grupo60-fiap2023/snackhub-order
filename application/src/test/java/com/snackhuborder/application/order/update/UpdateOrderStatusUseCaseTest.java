package com.snackhuborder.application.order.update;

import com.snackhuborder.application.order.OrderOutput;
import com.snackhuborder.domain.exceptions.DomainException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UpdateOrderStatusUseCaseTest {

    private UpdateOrderStatusUseCase useCase;

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
    void givenAValidCommand_whenCallUseCase_thenReturnOutput() throws Exception {
        OrderItem orderItem = OrderItem.with(OrderItemId.from(1L), "Snack", BigDecimal.TEN, 2, OrderItemCategory.SNACK);
        final var expectedCustomerId = 11l;
        Order order = Order.with(OrderId.from(1L), Arrays.asList(orderItem), expectedCustomerId, "Maria",null, OrderStatus.RECEIVED, Instant.now());

        when(this.orderGateway.findOrderById(any(OrderId.class)))
                .thenReturn(Optional.of(order));

        when(this.orderGateway.update(any(Order.class)))
                .thenAnswer(i -> i.getArgument(0));

        UpdateOrderStatusCommand command = UpdateOrderStatusCommand.with(1L, OrderStatus.FINISHED);

        this.useCase = new UpdateOrderStatusUseCase(this.orderGateway);
        OrderOutput orderOutput = this.useCase.execute(command);

        Assertions.assertNotNull(orderOutput);
        Assertions.assertEquals(OrderStatus.FINISHED, orderOutput.status());
        Assertions.assertNull(orderOutput.observation());
        Assertions.assertEquals(1, orderOutput.items().size());
        Assertions.assertNotNull(orderOutput.creationDate());
    }

    @Test
    void givenAOrderCommand_whenCallUseCase_thenReturnNotFindOrder() {
        when(this.orderGateway.findOrderById(any(OrderId.class)))
                .thenReturn(Optional.empty());

        UpdateOrderStatusCommand command = UpdateOrderStatusCommand.with(1L, OrderStatus.FINISHED);

        this.useCase = new UpdateOrderStatusUseCase(this.orderGateway);
        assertThatThrownBy(() -> this.useCase.execute(command))
                .isInstanceOf(DomainException.class);
    }
}
