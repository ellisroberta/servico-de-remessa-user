package com.example.servicoderemessauser.messaging;

import com.example.servicoderemessauser.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;

@Service
public class UserEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey.userCreated}")
    private String userCreatedRoutingKey;

    @Value("${rabbitmq.routingkey.userUpdated}")
    private String userUpdatedRoutingKey;

    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreatedEvent(User user) {
        rabbitTemplate.convertAndSend(exchange, userCreatedRoutingKey, user);
    }

    public void publishUserUpdatedEvent(User user) {
        rabbitTemplate.convertAndSend(exchange, userUpdatedRoutingKey, user);
    }

    // Exemplo de envio de mensagem customizada
    public void sendMessage(String queueName, String message) {
        rabbitTemplate.send(queueName, new Message(message.getBytes()));
    }

    public void sendUserCreatedEvent(User user) {
        rabbitTemplate.convertAndSend("userQueue", user);
    }
}

