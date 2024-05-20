package com.example.servicoderemessauser.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.servicoderemessauser.service.UserService;
import com.example.servicoderemessauser.model.User;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servico-de-remessa/users")
@Api(tags = "Usuários", description = "Endpoints para operações relacionadas aos usuários")
public class UserController {

    @Autowired
    private UserService userService;

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
        // Validate email, CPF, and CNPJ uniqueness
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (user.getCpf() != null && userService.findByCpf(user.getCpf()).isPresent()) {
            throw new RuntimeException("CPF already exists");
        }
        if (user.getCnpj() != null && userService.findByCnpj(user.getCnpj()).isPresent()) {
            throw new RuntimeException("CNPJ already exists");
        }
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
    }
}
