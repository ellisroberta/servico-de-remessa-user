package com.example.servicoderemessauser.messaging;

import com.example.servicoderemessauser.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitSender {

    private RabbitTemplate rabbitTemplate;

    public void sendTransactionMessage(TransactionMessage message) {
        rabbitTemplate.convertAndSend(RabbitConfig.TRANSACTION_QUEUE, message);
    }
    
}