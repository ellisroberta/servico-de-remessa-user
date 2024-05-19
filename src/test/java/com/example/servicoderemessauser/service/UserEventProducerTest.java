package com.example.servicoderemessauser.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserEventProducerTest {

    @Mock
    private AmqpTemplate rabbitTemplate;

    @InjectMocks
    private UserEventProducer userEventProducer;

    @Test
    public void testSendUserEvent() {
        // Mensagem simulada para enviar
        String message = "Test message";

        // Chama o método para enviar o evento do usuário
        userEventProducer.sendUserEvent(message);

        // Verifica se o rabbitTemplate.convertAndSend foi chamado corretamente
        verify(rabbitTemplate).convertAndSend("exchange-name", "routing-key.user", message);
    }
}
