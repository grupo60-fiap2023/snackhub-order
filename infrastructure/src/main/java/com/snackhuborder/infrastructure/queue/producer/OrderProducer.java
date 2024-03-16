package com.snackhuborder.infrastructure.queue.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snackhuborder.infrastructure.order.models.queue.OrderSuccessfulSchema;
import com.snackhuborder.infrastructure.order.models.queue.OrderSchema;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    @Autowired
    private SqsTemplate sqsTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;


    public void sendOrderSuccessful(OrderSuccessfulSchema schema) throws Exception {
        String sqsUrlOrderSuccessful = environment.getProperty("cloud.sqs.order-successful");
        String orderSuccessfulJson = objectMapper.writeValueAsString(schema);
        System.out.println("Produce to Order Successful: " + orderSuccessfulJson);
        sqsTemplate.send(sqsUrlOrderSuccessful, orderSuccessfulJson);
    }

    public void sendOrder(OrderSchema schema) throws Exception {
        String sqsUrlOrderStatus = environment.getProperty("cloud.sqs.order");
        String orderJson = objectMapper.writeValueAsString(schema);
        System.out.println("Produce to Order: " + orderJson);
        sqsTemplate.send(sqsUrlOrderStatus, orderJson);
    }
}
