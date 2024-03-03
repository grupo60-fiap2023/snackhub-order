package com.snackhuborder.infrastructure.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snackhuborder.domain.order.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public record UpdateStatusRequest(@JsonProperty("status") @NotNull @Schema(description = "Estado do pedido", example = "RECEIVED, IN_PREPARATION, READY ou FINISHED") OrderStatus status) {
}
