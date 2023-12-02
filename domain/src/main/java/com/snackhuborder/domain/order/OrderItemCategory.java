package com.snackhuborder.domain.order;

public enum OrderItemCategory {


    BEVERAGES("Bebidas"),
    DESSERT("Sobremesa"),
    SNACK("Lanche"),
    ACCOMPANIMENT("Acompanhamento");

    private final String name;

    OrderItemCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
