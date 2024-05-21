package com.example.servicoderemessauser.service;

import com.example.servicoderemessauser.messaging.UserEventPublisher;
import com.example.servicoderemessauser.model.User;
import com.example.servicoderemessauser.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserEventPublisher userEventPublisher;

    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public Optional<User> findByCpf(String cpf) {
        return Optional.ofNullable(userRepository.findByCpf(cpf));
    }

    public Optional<User> findByCnpj(String cnpj) {
        return Optional.ofNullable(userRepository.findByCnpj(cnpj));
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
        userEventPublisher.sendUserCreatedEvent(user);

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