package com.snackhuborder.domain.order;

public enum OrderStatus {

    RECEIVED(1,"Recebido"),
    IN_PREPARATION(2, "Em preparacao"),
    READY(3, "Pronto"),
    FINISHED(4,"Finalizado");

    private final Integer id;
    private final String name;

    OrderStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
