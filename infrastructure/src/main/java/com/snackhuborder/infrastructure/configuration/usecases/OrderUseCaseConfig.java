package com.snackhuborder.infrastructure.configuration.usecases;

import com.snackhuborder.application.order.create.CreateOrderUseCase;
import com.snackhuborder.application.order.retrieve.FindAllOrdersUseCase;
import com.snackhuborder.application.order.retrieve.FindOrdersByStatusUseCase;
import com.snackhuborder.application.order.update.UpdateOrderStatusUseCase;
import com.snackhuborder.domain.order.OrderGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderUseCaseConfig {

    private final OrderGateway orderGateway;

    public OrderUseCaseConfig(final OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(){
        return new CreateOrderUseCase(orderGateway);
    }

    @Bean
    public FindAllOrdersUseCase findAllOrders() {
        return new FindAllOrdersUseCase(orderGateway);
    }

    @Bean
    public FindOrdersByStatusUseCase findOrdersByStatus() {
        return new FindOrdersByStatusUseCase(orderGateway);
    }

    @Bean
    public UpdateOrderStatusUseCase updateOrderStatus() {
        return new UpdateOrderStatusUseCase(orderGateway);
    }
}
