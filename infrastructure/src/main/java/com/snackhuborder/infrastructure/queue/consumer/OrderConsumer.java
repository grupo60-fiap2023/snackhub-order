package com.snackhuborder.infrastructure.queue.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.snackhuborder.application.order.update.UpdateOrderStatusCommand;
import com.snackhuborder.application.order.update.UpdateOrderStatusUseCase;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.models.queue.UpdateStatusSchema;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public OrderConsumer(final UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @SqsListener("${cloud.sqs.order-status}")
    public void listen(String message) throws Exception {
        UpdateStatusSchema status = objectMapper.readValue(message, UpdateStatusSchema.class);
        var command = UpdateOrderStatusCommand.with(status.orderId(), OrderStatus.valueOf(status.status()));
        this.updateOrderStatusUseCase.execute(command);
    }
}
