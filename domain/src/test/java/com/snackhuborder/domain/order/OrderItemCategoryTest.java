package com.snackhuborder.domain.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class OrderItemCategoryTest {

    @Test
    public void givenAValidSnackCategory_whenCallNameCateroy_thenReturnNameFilled() {

        Assertions.assertNotNull(OrderItemCategory.SNACK.getName());
        Assertions.assertEquals("Lanche", OrderItemCategory.SNACK.getName());
    }

    @Test
    public void givenAValidBeveragesCategory_whenCallNameCateroy_thenReturnNameFilled() {

        Assertions.assertNotNull(OrderItemCategory.BEVERAGES.getName());
        Assertions.assertEquals("Bebidas", OrderItemCategory.BEVERAGES.getName());
    }

    @Test
    public void givenAValidDessertCategory_whenCallNameCateroy_thenReturnNameFilled() {

        Assertions.assertNotNull(OrderItemCategory.DESSERT.getName());
        Assertions.assertEquals("Sobremesa", OrderItemCategory.DESSERT.getName());
    }

    @Test
    public void givenAValidAccompanimentCategory_whenCallNameCateroy_thenReturnNameFilled() {

        Assertions.assertNotNull(OrderItemCategory.ACCOMPANIMENT.getName());
        Assertions.assertEquals("Acompanhamento", OrderItemCategory.ACCOMPANIMENT.getName());
    }
}
