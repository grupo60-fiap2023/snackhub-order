package com.snackhuborder.infrastructure.api;

import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.models.CreateOrderRequest;
import com.snackhuborder.infrastructure.order.models.OrderResponse;
import com.snackhuborder.infrastructure.order.models.UpdateStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "orders")
@Tag(name = "Orders")
public interface OrderAPI {

    @PostMapping(value = "createOrder",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderRequest input);

    @GetMapping("/list")
    @Operation(summary = "List all orders with priority states orders READY > IN_PREPARATION > RECEIVED and order by received date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<List<OrderResponse>>  listAllOrders();

    @GetMapping("/{status}")
    @Operation(summary = "List all orders by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<List<OrderResponse>>  listOrdersByStatus(@PathVariable(name = "status") OrderStatus status);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update order by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> updateStatusById(@PathVariable(name = "id") Long id, @RequestBody UpdateStatusRequest request);
}
