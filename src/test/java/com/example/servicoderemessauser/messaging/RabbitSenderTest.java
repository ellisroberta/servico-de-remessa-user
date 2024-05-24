package com.example.servicoderemessauser.messaging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitSender rabbitSender;

    @Test
    @DisplayName("Deve enviar mensagem de transação para a fila")
    void testSendTransactionMessage() {

        ReflectionTestUtils.setField(rabbitSender, "exchange", "exchangeTest");
        ReflectionTestUtils.setField(rabbitSender, "transactionRoutingKey", "transactionRoutingKeyTest");

        TransactionMessage message = new TransactionMessage(UUID.randomUUID(), new BigDecimal("100.00"));

        rabbitSender.sendTransactionMessage(message);

        verify(rabbitTemplate).convertAndSend("exchangeTest", "transactionRoutingKeyTest", message);
    }
}
