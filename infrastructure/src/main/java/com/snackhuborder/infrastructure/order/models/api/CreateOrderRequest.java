package com.snackhuborder.infrastructure.order.models.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreateOrderRequest (
        @JsonProperty("customerId") @Schema(description = "Identificador do cliente", example = "3437524e-1607-11ee-be56-0242ac120002") Long customerId,
        @JsonProperty("orderItems") @Schema(description = "Itens do pedido") List<OrderItemRequest> items,
        @JsonProperty("orderIdentifier") @Schema(description = "Identificador do pedido, no qual o atendente chamara", example = "Maria") String orderIdentifier,
        @JsonProperty("observation") @Schema(description = "Observação do pedido", example = "Sem molho") String observation
) {
}
