package com.snackhuborder.domain.order;


import com.snackhuborder.domain.validation.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderItemTest {

    public static final long ONE_ID = 1l;
    public static final int TWO_QUANTITY = 2;
    public static final String SNACK_NAME = "Hamburguer";

    @Test
    public void givenAValidParams_whenCallWithOrderItem_thenInstantiateAOrder() {
        final var expectedName = SNACK_NAME;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItemId id = OrderItemId.from(ONE_ID);
        OrderItem item = OrderItem.with(id, expectedName, expectedPrice, expectedQuantity, expectedCategory);

        Assertions.assertNotNull(item);
        Assertions.assertNotNull(item.getId());
        Assertions.assertEquals(id.getValue(), item.getId().getValue());
        Assertions.assertEquals(expectedName, item.getName());
        Assertions.assertEquals(expectedPrice, item.getPrice());
        Assertions.assertEquals(expectedCategory, item.getCategory());
        Assertions.assertEquals(expectedQuantity, item.getQuantity());
    }

    @Test
    public void givenAValidParams_whenCallNewOrderItem_thenInstantiateAOrder() {
        final var expectedName = SNACK_NAME;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItem item = OrderItem.newOrderItem(expectedName, expectedPrice, expectedQuantity, expectedCategory);

        Assertions.assertNotNull(item);
        Assertions.assertNull(item.getId());
        Assertions.assertEquals(expectedName, item.getName());
        Assertions.assertEquals(expectedPrice, item.getPrice());
        Assertions.assertEquals(expectedCategory, item.getCategory());
        Assertions.assertEquals(expectedQuantity, item.getQuantity());
    }

    @Test
    public void givenZeroQuantity_whenCallNewOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'quantity' must be greater than zero";

        final var expectedName = SNACK_NAME;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedCategory = OrderItemCategory.SNACK;

        OrderItem item = OrderItem.newOrderItem(expectedName, expectedPrice, 0, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }

    @Test
    public void givenInvalidQuantity_whenCallNewOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'quantity' must be greater than zero";

        final var expectedName = SNACK_NAME;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedCategory = OrderItemCategory.SNACK;

        OrderItem item = OrderItem.newOrderItem(expectedName, expectedPrice, null, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }

    @Test
    public void givenANegativeQuantity_whenCallNewOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'quantity' must be greater than zero";

        final var expectedName = SNACK_NAME;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedCategory = OrderItemCategory.SNACK;

        OrderItem item = OrderItem.newOrderItem(expectedName, expectedPrice, -1, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }

    @Test
    public void givenANegativePrice_whenCallWithOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'price' should not be negative";

        final var expectedName = SNACK_NAME;
        final var expectedPrice = BigDecimal.valueOf(-1);
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItem item = OrderItem.newOrderItem(expectedName, expectedPrice, expectedQuantity, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }

    @Test
    public void givenANullCategory_whenCallWithOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'category' is required";

        final var expectedName = SNACK_NAME;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItem item = OrderItem.newOrderItem(expectedName, expectedPrice, expectedQuantity, null);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }
    @Test
    public void givenANullPrice_whenCallWithOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'price' is required";

        final var expectedName = SNACK_NAME;
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItem item = OrderItem.newOrderItem(expectedName, null, expectedQuantity, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }

    @Test
    public void givenANullName_whenCallWithOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' is required";

        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItem item = OrderItem.newOrderItem(null, expectedPrice, expectedQuantity, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }

    @Test
    public void givenABlankName_whenCallWithOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItem item = OrderItem.newOrderItem("", expectedPrice, expectedQuantity, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }

    @Test
    public void givenAInvalidNameMore255Chars_whenCallWithOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final String expectedName = """
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                """;
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItem item = OrderItem.newOrderItem(expectedName, expectedPrice, expectedQuantity, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }

    @Test
    public void givenAInvalidNameLess3Chars_whenCallWithOrderItem_thenReturnError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final String expectedName = "x";
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedPrice = BigDecimal.TEN;
        final var expectedQuantity = TWO_QUANTITY;

        OrderItem item = OrderItem.newOrderItem(expectedName, expectedPrice, expectedQuantity, expectedCategory);

        Notification notification = Notification.create();
        item.validate(notification);

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().stream().findFirst().get().message());
    }
}