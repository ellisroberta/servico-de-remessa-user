package com.example.servicoderemessauser.handler;

import org.springframework.stereotype.Component;

@Component
public class UserEventHandler {
    public void handleUserCreatedEvent(String message) {
        // Implemente o código para processar o evento de usuário criado
        System.out.println("Evento de usuário criado recebido: " + message);
    }
}
