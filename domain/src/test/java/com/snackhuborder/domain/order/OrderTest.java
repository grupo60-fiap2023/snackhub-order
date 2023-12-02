package com.snackhuborder.domain.order;


import com.snackhuborder.domain.validation.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderTest {

    private OrderItem getOrderItemMock(){
        final var expectedName = "Big Mac";
        final var expectedPrice = BigDecimal.valueOf(25.0);
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = 2;

        return OrderItem.newOrderItem(expectedName, expectedPrice, expectedQuantity, expectedCategory);
    }

    @Test
    public void givenAValidParams_whenCallNewOrder_thenInstantiateAOrder() {
        final var expectedCustomerId = 11l;
        OrderItem item = getOrderItemMock();
        final Order order = Order.newOrder(Arrays.asList(item), expectedCustomerId, "Maria",null);

        Assertions.assertNotNull(order);

        Notification notification = Notification.create();
        order.validate(notification);
        Assertions.assertFalse(notification.hasError());
        Assertions.assertNull(notification.firstError());

        Assertions.assertNotNull(order.getCustomerId());
        Assertions.assertNotNull(order.getCreatedAt());
        Assertions.assertNull(order.getObservation());
        Assertions.assertEquals(OrderStatus.RECEIVED, order.getStatus());
        Assertions.assertEquals(item, order.getItems().stream().findFirst().get());
    }

    @Test
    public void givenAValidParams_whenCallWithOrder_thenInstantiateAOrder() {
        final var expectedCustomerId = 11l;
        OrderItem item = getOrderItemMock();
        String observation = "Sem molho";
        final Order order = Order.with(OrderId.from(1L), Arrays.asList(item), expectedCustomerId, "Maria", observation, OrderStatus.RECEIVED, Instant.now());

        Assertions.assertNotNull(order);

        Notification notification = Notification.create();
        order.validate(notification);
        Assertions.assertFalse(notification.hasError());
        Assertions.assertNull(notification.firstError());

        Assertions.assertNotNull(order.getId().getValue());
        Assertions.assertNotNull(order.getCustomerId());
        Assertions.assertNotNull(order.getCreatedAt());
        Assertions.assertEquals(observation, order.getObservation());
        Assertions.assertEquals(OrderStatus.RECEIVED, order.getStatus());
        Assertions.assertEquals(item, order.getItems().stream().findFirst().get());
    }

    @Test
    public void givenAInvalidObservation_whenCallNewOrdertAndValidate_thenShouldReceiveError() {
        final String expectedObservation = """
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                """;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'observation' must be between 1 and 255 characters";

        OrderItem item = getOrderItemMock();
        final var expectedCustomerId = 11l;
        final Order order = Order.newOrder(Arrays.asList(item), expectedCustomerId, "Maria", expectedObservation);

        Notification notification = Notification.create();
        order.validate(notification);

        Assertions.assertTrue(notification.hasError());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
    }

    @Test
    public void givenAInvalidOrderItems_whenCallNewOrdertAndValidate_thenShouldReceiveError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "The order is necessary almost one item";

        final var expectedCustomerId = 11l;
        final Order order = Order.newOrder(null, expectedCustomerId, "Maria",null);

        Notification notification = Notification.create();
        order.validate(notification);

        Assertions.assertTrue(notification.hasError());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
    }

    @Test
    public void givenAInvalidEmptyOrderItems_whenCallNewOrdertAndValidate_thenShouldReceiveError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "The order is necessary almost one item";

        final var expectedCustomerId = 11l;
        final Order order = Order.newOrder(new ArrayList<>(), expectedCustomerId, "Maria",null);

        Notification notification = Notification.create();
        order.validate(notification);

        Assertions.assertTrue(notification.hasError());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
    }

    @Test
    public void givenAValidStatus_whenCallChangeStatus_thenChangeToNewState() {
        final var expectedCustomerId = 11l;
        OrderItem item = getOrderItemMock();
        final Order order = Order.newOrder(Arrays.asList(item), expectedCustomerId, "Maria",null);
        order.changeStatus(OrderStatus.IN_PREPARATION);

        Assertions.assertEquals(OrderStatus.IN_PREPARATION, order.getStatus());
    }

    @Test
    public void givenAInvalidOrderIdentifier_whenCallNewOrdertAndValidate_thenShouldReceiveError() {
        final String expectedOrderIdentifier = """
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                """;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'orderIdentifier' must be between 1 and 50 characters";

        OrderItem item = getOrderItemMock();
        final var expectedCustomerId = 11l;
        final Order order = Order.newOrder(Arrays.asList(item), expectedCustomerId, expectedOrderIdentifier, null);

        Notification notification = Notification.create();
        order.validate(notification);

        Assertions.assertTrue(notification.hasError());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
    }

    @Test
    public void givenANulldOrderIdentifier_whenCallNewOrdertAndValidate_thenShouldReceiveError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'orderIdentifier' is required";

        OrderItem item = getOrderItemMock();
        final var expectedCustomerId = 11l;
        final Order order = Order.newOrder(Arrays.asList(item), expectedCustomerId, null, null);

        Notification notification = Notification.create();
        order.validate(notification);

        Assertions.assertTrue(notification.hasError());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
    }

    @Test
    public void givenAEmptyOrderIdentifier_whenCallNewOrdertAndValidate_thenShouldReceiveError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'orderIdentifier' is required";

        OrderItem item = getOrderItemMock();
        final var expectedCustomerId = 11l;
        final Order order = Order.newOrder(Arrays.asList(item), expectedCustomerId, "", null);

        Notification notification = Notification.create();
        order.validate(notification);

        Assertions.assertTrue(notification.hasError());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
    }
}
