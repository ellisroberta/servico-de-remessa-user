package com.example.servicoderemessauser.handler;

import org.springframework.stereotype.Component;

@Component
public class UserEventHandler {
    public void handleUserCreatedEvent(String message) {
        System.out.println("Evento de usuário criado recebido: " + message);
    }
}
