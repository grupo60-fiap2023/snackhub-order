package com.snackhuborder.application.order.create;

import com.snackhuborder.application.order.OrderOutput;
import com.snackhuborder.domain.exceptions.DomainException;
import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderGateway;
import com.snackhuborder.domain.order.OrderItemCategory;
import com.snackhuborder.domain.order.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateOrderUseCaseTest {

    private CreateOrderUseCase useCase;

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
    public void givenAValidCommand_whenUseCase_thenReturnOutput() {
        CreateOrderItemCommand firstItemCommand = CreateOrderItemCommand.with(
                "Snack", BigDecimal.TEN, 2, OrderItemCategory.SNACK);
        CreateOrderItemCommand secondItemCommand = CreateOrderItemCommand.with(
                "Soda", BigDecimal.ONE, 1, OrderItemCategory.BEVERAGES);

        when(this.orderGateway.save(any(Order.class)))
                .thenAnswer(i -> i.getArgument(0));

        final var expectedCustomerId = 11l;
        CreateOrderCommand command = CreateOrderCommand.with(
                Arrays.asList(firstItemCommand, secondItemCommand), expectedCustomerId, "Maria", "Sem Molho");

        this.useCase = new CreateOrderUseCase(this.orderGateway);
        OrderOutput orderOutput = this.useCase.execute(command);

        Assertions.assertNotNull(orderOutput);
        Assertions.assertEquals(OrderStatus.RECEIVED, orderOutput.status());
        Assertions.assertEquals("Sem Molho", orderOutput.observation());
        Assertions.assertEquals(2, orderOutput.items().size());
        Assertions.assertNotNull(orderOutput.creationDate());
    }

    @Test
    public void givenAInvalidCommand_whenUseCase_thenErro() {
        when(this.orderGateway.save(any(Order.class)))
                .thenAnswer(i -> i.getArgument(0));

        final var expectedCustomerId = 11l;
        CreateOrderCommand command = CreateOrderCommand.with(null, expectedCustomerId, "Maria", null);

        this.useCase = new CreateOrderUseCase(this.orderGateway);
        assertThatThrownBy(() -> this.useCase.execute(command))
                .isInstanceOf(DomainException.class);
    }
}
