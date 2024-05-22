package com.example.servicoderemessauser.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey.transaction}")
    private String transactionRoutingKey;

    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTransactionMessage(TransactionMessage message) {
        rabbitTemplate.convertAndSend(exchange, transactionRoutingKey, message);
    }
    
}