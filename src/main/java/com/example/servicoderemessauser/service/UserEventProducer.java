package com.example.servicoderemessauser.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendUserEvent(String message) {
        rabbitTemplate.convertAndSend("exchange-name", "routing-key.user", message);
    }
}

