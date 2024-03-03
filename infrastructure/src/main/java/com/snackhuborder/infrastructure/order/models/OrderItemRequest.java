package com.snackhuborder.infrastructure.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snackhuborder.domain.order.OrderItemCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderItemRequest(
        @JsonProperty("name") @NotBlank @Schema(description = "Nome do produto", example = "Hambúrguer") String name,
        @JsonProperty("price") @NotNull @Schema(description = "Preço do produto", example = "35.99") BigDecimal price,
        @JsonProperty("quantity") @NotNull @Schema(description = "Quantidade do item(ns)", example = "2") Integer quantity,
        @JsonProperty ("category") @NotNull @Schema(description = "Categoria do produto, verificar os Enums validos", example = "SNACK") OrderItemCategory category
) {
}
