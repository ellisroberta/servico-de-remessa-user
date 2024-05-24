package com.example.servicoderemessauser.messaging;

import com.example.servicoderemessauser.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private UserEventPublisher userEventPublisher;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(userEventPublisher, "exchange", "testExchange");
        ReflectionTestUtils.setField(userEventPublisher, "userCreatedRoutingKey", "test.user.created");
        ReflectionTestUtils.setField(userEventPublisher, "userUpdatedRoutingKey", "test.user.updated");
    }

    @Test
    @DisplayName("Deve publicar evento de criação de usuário")
    void testPublishUserCreatedEvent() {
        User user = new User();
        user.setFullName("username");
        user.setEmail("user@example.com");

        userEventPublisher.publishUserCreatedEvent(user);

        verify(rabbitTemplate).convertAndSend("testExchange", "test.user.created", user);
    }

    @Test
    @DisplayName("Deve publicar evento de atualização de usuário")
    void testPublishUserUpdatedEvent() {
        User user = new User();
        user.setFullName("username");
        user.setEmail("user@example.com");

        userEventPublisher.publishUserUpdatedEvent(user);

        verify(rabbitTemplate).convertAndSend("testExchange", "test.user.updated", user);
    }

    @Test
    @DisplayName("Teste de envio de mensagem para fila")
    void testSendMessageToQueue() {
        String exchange = "exchange-name";
        String routingKey = "routing-key";
        String message = "Exemplo de mensagem para enviar";

        rabbitTemplate.convertAndSend(exchange, routingKey, message);

        verify(rabbitTemplate, times(1)).convertAndSend(exchange, routingKey, message);
    }
}
