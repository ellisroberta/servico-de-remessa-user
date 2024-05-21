package com.example.servicoderemessauser.controller;

import com.example.servicoderemessauser.messaging.TransactionMessage;
import com.example.servicoderemessauser.messaging.TransactionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.servicoderemessauser.service.UserService;
import com.example.servicoderemessauser.messaging.RabbitSender;
import com.example.servicoderemessauser.model.User;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Api(tags = "Usuários", description = "Endpoints para operações relacionadas aos usuários")
public class UserController {

    private UserService userService;

    private RabbitSender rabbitSender;

    public UserController(UserService userService, RabbitSender rabbitSender) {
        this.userService = userService;
        this.rabbitSender = rabbitSender;
    }

    @ApiOperation(value = "Listar todos os usuários",
            notes = "Retorna uma lista de todos os usuários registrados.")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @ApiOperation(value = "Obter informações de um usuário",
            notes = "Retorna informações detalhadas de um usuário baseado no ID.")
    @GetMapping("/{userId}")
    public User getUser(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @ApiOperation(value = "Registrar um novo usuário",
            notes = "Cria um novo usuário com base nos dados fornecidos.")
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @ApiOperation(value = "Deletar um usuário",
            notes = "Remove um usuário do sistema com base no ID fornecido.")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
    }

    @ApiOperation(value = "Criar uma transação",
            notes = "Envia uma solicitação de transação para o RabbitMQ.")
    @PostMapping("/transacao")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest request) {
        TransactionMessage message = new TransactionMessage(request.getUserId(), request.getAmount(), request.getCurrency());
        rabbitSender.sendTransactionMessage(message);
        return ResponseEntity.ok("Transaction request sent.");
    }
}
