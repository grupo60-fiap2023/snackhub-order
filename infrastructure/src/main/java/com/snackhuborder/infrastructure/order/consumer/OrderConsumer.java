package com.snackhuborder.infrastructure.order.consumer;


import com.snackhuborder.application.order.update.UpdateOrderStatusCommand;
import com.snackhuborder.application.order.update.UpdateOrderStatusUseCase;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.models.UpdateStatusMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;


@Component
public class OrderConsumer {

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public OrderConsumer(final UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @SqsListener("order-status")
    public void listen(UpdateStatusMessage message){
        try{
            var command = UpdateOrderStatusCommand.with(message.id(), OrderStatus.valueOf(message.status()));
            this.updateOrderStatusUseCase.execute(command);
        }catch (Exception e){
            //todo produzir para topico de deadletters
        }
    }
}
