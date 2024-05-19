package com.example.servicoderemessauser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.servicoderemessauser.repository.UserRepository;
import com.example.servicoderemessauser.service.UserEventProducer;
import com.example.servicoderemessauser.model.User;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventProducer userEventProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }
    public User createUser(User user) {
        // Verifica se o email já está registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Verifica se o CPF já está registrado
        if (user.getCpf() != null && userRepository.findByCpf(user.getCpf()) != null) {
            throw new IllegalArgumentException("CPF already registered");
        }

        // Verifica se o CNPJ já está registrado
        if (user.getCnpj() != null && userRepository.findByCnpj(user.getCnpj()) != null) {
            throw new IllegalArgumentException("CNPJ already registered");
        }

        // Criptografa a senha usando BCrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Envia evento para o RabbitMQ
        userEventProducer.sendUserEvent("Novo usuário criado: " + user.getFullName());

        // Salva o usuário no banco de dados
        return userRepository.save(user);
    }

    public User updateUser(UUID userId, User updatedUser) {
        // Verifica se o usuário existe
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Atualiza os campos do usuário existente
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        // Salva e retorna o usuário atualizado
        return userRepository.save(existingUser);
    }
}