package com.example.servicoderemessauser.controller;

import com.example.servicoderemessauser.dto.TransactionRequestDTO;
import com.example.servicoderemessauser.dto.UserDTO;
import com.example.servicoderemessauser.messaging.RabbitSender;
import com.example.servicoderemessauser.messaging.TransactionMessage;
import com.example.servicoderemessauser.model.User;
import com.example.servicoderemessauser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Api(tags = "Usuários")
public class UserController {

    private final UserService userService;
    private final RabbitSender rabbitSender;

    public UserController(UserService userService, RabbitSender rabbitSender) {
        this.userService = userService;
        this.rabbitSender = rabbitSender;
    }

    @ApiOperation(value = "Listar todos os usuários",
            notes = "Retorna uma lista de todos os usuários registrados.")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @ApiOperation(value = "Obter informações de um usuário",
            notes = "Retorna informações detalhadas de um usuário baseado no ID.")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            UserDTO userDTO = convertToDTO(user);
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Registrar um novo usuário",
            notes = "Cria um novo usuário com base nos dados fornecidos.")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user = userService.save(user);
        UserDTO newUserDTO = convertToDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
    }

    @ApiOperation(value = "Criar uma transação",
            notes = "Envia uma solicitação de transação para o RabbitMQ.")
    @PostMapping("/transacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createTransaction(@RequestBody TransactionRequestDTO requestDTO) {
        TransactionMessage message = new TransactionMessage(requestDTO.getUserId(), requestDTO.getAmountBrl());
        rabbitSender.sendTransactionMessage(message);
    }

    @ApiOperation(value = "Deletar um usuário",
            notes = "Remove um usuário do sistema com base no ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setDocument(user.getDocument());
        dto.setUserType(user.getUserType());
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setDocument(dto.getDocument());
        user.setUserType(dto.getUserType());
        return user;
    }
}