package com.snackhuborder.infrastructure.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snackhuborder.domain.order.OrderItemCategory;

import java.math.BigDecimal;

public record OrderItemResponse(@JsonProperty("id") Long id,
                                @JsonProperty("name") String name,
                                @JsonProperty("price") BigDecimal price,
                                @JsonProperty("quantity") Integer quantity,
                                @JsonProperty OrderItemCategory category){
}
