package com.snackhuborder.infrastructure.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OrderResponse (
        @JsonProperty("id") Long id,
        @JsonProperty("items") List<OrderItemResponse> items,
        @JsonProperty("orderIdentifier") String orderIdentifier,
        @JsonProperty("observation") String observation,
        @JsonProperty("status") String status

) {
}
