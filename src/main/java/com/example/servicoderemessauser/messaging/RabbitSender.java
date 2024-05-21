package com.example.servicoderemessauser.messaging;

import com.example.servicoderemessauser.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTransactionMessage(TransactionMessage message) {
        rabbitTemplate.convertAndSend(RabbitConfig.TRANSACTION_QUEUE, message);
    }
    
}