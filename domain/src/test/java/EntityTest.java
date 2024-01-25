
import com.snackhuborder.domain.order.OrderItem;
import com.snackhuborder.domain.order.OrderItemCategory;
import com.snackhuborder.domain.order.OrderItemId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;


class EntityTest {

    @Test
    void givenASameEntity_whenEquals_thenReturnTrue() {
        final var expectedName = "Big Mac";
        final var expectedPrice = BigDecimal.valueOf(25.0);
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = 2;

        OrderItemId id = OrderItemId.from(1L);
        OrderItem firstOrderItem = OrderItem.with(id, expectedName, expectedPrice, expectedQuantity, expectedCategory);

        OrderItem secondOrderItem = firstOrderItem;
        Assertions.assertEquals(firstOrderItem, secondOrderItem);
    }

    @Test
    void givenADiferentEntity_whenEquals_thenReturnFalse() {
        final var expectedName = "Big Mac";
        final var expectedPrice = BigDecimal.valueOf(25.0);
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = 2;

        OrderItemId id = OrderItemId.from(1L);
        OrderItem firstOrderItem = OrderItem.with(id, expectedName, expectedPrice, expectedQuantity, expectedCategory);

        OrderItemId secondId = OrderItemId.from(1L);
        OrderItem secondOrderItem = OrderItem.with(secondId, expectedName, expectedPrice, expectedQuantity, expectedCategory);

        Assertions.assertNotEquals(firstOrderItem, secondOrderItem);
    }

    @Test
    void givenAEntity_whenCallHash_thenReturnHashNotNull() {
        final var expectedName = "Big Mac";
        final var expectedPrice = BigDecimal.valueOf(25.0);
        final var expectedCategory = OrderItemCategory.SNACK;
        final var expectedQuantity = 2;

        OrderItemId id = OrderItemId.from(1L);
        OrderItem firstOrderItem = OrderItem.with(id, expectedName, expectedPrice, expectedQuantity, expectedCategory);
        Assertions.assertEquals(firstOrderItem.hashCode(), firstOrderItem.hashCode());
    }
}
