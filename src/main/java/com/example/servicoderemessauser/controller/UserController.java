package com.example.servicoderemessauser.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.servicoderemessauser.service.UserService;
import com.example.servicoderemessauser.model.User;

import java.util.UUID;

@RestController
@RequestMapping("/servico-de-remessa/users")
@Api(tags = "Usuários", description = "Endpoints para operações relacionadas aos usuários")
public class UserController {

    @Autowired
    private UserService userService;

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
        return userService.createUser(user);
    }
}
