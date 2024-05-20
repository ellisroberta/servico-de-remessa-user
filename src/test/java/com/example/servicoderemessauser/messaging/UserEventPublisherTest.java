package com.example.servicoderemessauser.messaging;

import com.example.servicoderemessauser.messaging.UserEventPublisher;
import com.example.servicoderemessauser.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private UserEventPublisher userEventPublisher;

    @Test
    public void testSendUserEvent() {
// Criando um usuário simulado
        User user = new User();
        user.setFullName("username");
        user.setEmail("user@example.com");

        // Criando uma instância do UserEventPublisher injetando o rabbitTemplate mockado pelo construtor
        UserEventPublisher userEventPublisher = new UserEventPublisher(rabbitTemplate);

        // Chama o método para enviar o evento do usuário
        userEventPublisher.sendUserCreatedEvent(user);

        // Verifica se o rabbitTemplate.convertAndSend foi chamado corretamente
        verify(rabbitTemplate).convertAndSend("userQueue", user);
    }
}
