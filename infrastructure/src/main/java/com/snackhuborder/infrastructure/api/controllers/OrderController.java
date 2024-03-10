package com.snackhuborder.infrastructure.api.controllers;

import com.snackhuborder.application.order.create.CreateOrderCommand;
import com.snackhuborder.application.order.create.CreateOrderUseCase;
import com.snackhuborder.application.order.retrieve.FindAllOrdersUseCase;
import com.snackhuborder.application.order.retrieve.FindOrdersByStatusUseCase;
import com.snackhuborder.application.order.update.UpdateOrderStatusCommand;
import com.snackhuborder.application.order.update.UpdateOrderStatusUseCase;
import com.snackhuborder.domain.exceptions.DomainException;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.api.OrderAPI;
import com.snackhuborder.infrastructure.order.models.api.CreateOrderRequest;
import com.snackhuborder.infrastructure.order.models.api.OrderResponse;
import com.snackhuborder.infrastructure.order.models.api.UpdateStatusRequest;
import com.snackhuborder.infrastructure.order.presenters.OrderApiPresenter;
import com.snackhuborder.infrastructure.order.presenters.OrderItemApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class OrderController implements OrderAPI {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindAllOrdersUseCase findAllOrdersUseCase;
    private final FindOrdersByStatusUseCase findOrdersByStatusUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public OrderController(final CreateOrderUseCase createOrderUseCase,
                           final FindAllOrdersUseCase findAllOrdersUseCase,
                           final FindOrdersByStatusUseCase findOrdersByStatusUseCase,
                           final UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.findAllOrdersUseCase = findAllOrdersUseCase;
        this.findOrdersByStatusUseCase = findOrdersByStatusUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @Override
    public ResponseEntity<?> createOrder(CreateOrderRequest request) {
        OrderResponse response;
        try{
            var items = OrderItemApiPresenter.present(request.items());
            var command = CreateOrderCommand.with(items, request.customerId(), request.orderIdentifier(), request.observation());
            var order = this.createOrderUseCase.execute(command);
            response = OrderApiPresenter.present(order);
        }catch (DomainException e){
            return ResponseEntity.unprocessableEntity().body(e.getErrors());
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

        return ResponseEntity.created(URI.create("/orders" + response.id())).body(response);
    }

    @Override
    public ResponseEntity<List<OrderResponse>> listAllOrders() {
        return ResponseEntity.ok().body(this.findAllOrdersUseCase.execute().stream().map(OrderApiPresenter::present).toList());
    }

    @Override
    public ResponseEntity<List<OrderResponse>> listOrdersByStatus(OrderStatus status) {
        return ResponseEntity.ok().body(this.findOrdersByStatusUseCase.execute(status).stream().map(OrderApiPresenter::present).toList());
    }

    @Override
    public ResponseEntity<?> updateStatusById(Long id, UpdateStatusRequest request) {
        OrderResponse output;
        try{
            var command = UpdateOrderStatusCommand.with(id, request.status());
             var order = this.updateOrderStatusUseCase.execute(command);
            output = OrderApiPresenter.present(order);
        }catch (DomainException e){
            return ResponseEntity.unprocessableEntity().body(e.getErrors());
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

        return ResponseEntity.ok().body(output);
    }
}
