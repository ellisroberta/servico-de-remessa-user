package com.example.servicoderemessauser.messaging;

import com.example.servicoderemessauser.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String userCreatedRoutingKey;
    private final String userUpdatedRoutingKey;

    public UserEventPublisher(RabbitTemplate rabbitTemplate,
                              @Value("${rabbitmq.exchange}") String exchange,
                              @Value("${rabbitmq.routingkey.userCreated}") String userCreatedRoutingKey,
                              @Value("${rabbitmq.routingkey.userUpdated}") String userUpdatedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.userCreatedRoutingKey = userCreatedRoutingKey;
        this.userUpdatedRoutingKey = userUpdatedRoutingKey;
    }

    public void publishUserCreatedEvent(User user) {
        rabbitTemplate.convertAndSend(exchange, userCreatedRoutingKey, user);
    }

    public void publishUserUpdatedEvent(User user) {
        rabbitTemplate.convertAndSend(exchange, userUpdatedRoutingKey, user);
    }
}