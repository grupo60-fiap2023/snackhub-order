package com.snackhuborder.domain.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class OrderStatusTest {

    @Test
    void givenAReceivedStatus_whenCallStatusName_thenReturnNameFilled() {
        OrderStatus received = OrderStatus.RECEIVED;
        Assertions.assertNotNull(received.getName());
        Assertions.assertEquals("Recebido", received.getName());
        Assertions.assertEquals(1, received.getId());
    }

    @Test
    void givenAInPreparationStatus_whenCallStatusName_thenReturnNameFilled() {
        OrderStatus inPreparation = OrderStatus.IN_PREPARATION;
        Assertions.assertNotNull(inPreparation.getName());
        Assertions.assertEquals("Em preparacao", inPreparation.getName());
        Assertions.assertEquals(6, inPreparation.getId());
    }

    @Test
    void givenAFinishedStatus_whenCallStatusName_thenReturnNameFilled() {
        OrderStatus finished = OrderStatus.FINISHED;
        Assertions.assertNotNull(finished.getName());
        Assertions.assertEquals("Finalizado", finished.getName());
        Assertions.assertEquals(7, finished.getId());
    }
}
