package com.example.servicoderemessauser.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String transactionRoutingKey;

    public RabbitSender(RabbitTemplate rabbitTemplate,
                        @Value("${rabbitmq.exchange}") String exchange,
                        @Value("${rabbitmq.routingkey.transaction}") String transactionRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.transactionRoutingKey = transactionRoutingKey;
    }

    public void sendTransactionMessage(TransactionMessage message) {
        rabbitTemplate.convertAndSend(exchange, transactionRoutingKey, message);
    }
}