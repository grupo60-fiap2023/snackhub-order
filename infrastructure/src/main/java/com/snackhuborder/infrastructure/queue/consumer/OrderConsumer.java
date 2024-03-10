package com.snackhuborder.infrastructure.queue.consumer;


import com.snackhuborder.application.order.update.UpdateOrderStatusCommand;
import com.snackhuborder.application.order.update.UpdateOrderStatusUseCase;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.models.queue.UpdateStatusSchema;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;


@Component
public class OrderConsumer {

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public OrderConsumer(final UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @SqsListener("order-status-topic")
    public void listen(UpdateStatusSchema message) throws Exception {
        var command = UpdateOrderStatusCommand.with(message.orderId(), OrderStatus.valueOf(message.status()));
        this.updateOrderStatusUseCase.execute(command);
    }
}
