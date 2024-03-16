package com.snackhuborder.domain.order;

public enum OrderStatus {

    RECEIVED(1,"Recebido"),
    PENDING_PAYMENT(2, "Pagamento Pendente"),
    PAYMENT_ACCEPT(3, "Pagamento Aprovado"),
    PAYMENT_REJECT(4, "Pagamento Rejeitado"),
    WAIT_PREPARATION(5, "Aguardando Preparacao"),
    IN_PREPARATION(6, "Em preparacao"),
    FINISHED(7,"Finalizado");

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

    public boolean isPaymentAccept(){
        return PAYMENT_ACCEPT.equals(this);
    }
}
