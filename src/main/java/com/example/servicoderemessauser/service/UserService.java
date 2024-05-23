package com.example.servicoderemessauser.service;

import com.example.servicoderemessauser.messaging.UserEventPublisher;
import com.example.servicoderemessauser.model.User;
import com.example.servicoderemessauser.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserEventPublisher userEventPublisher;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserEventPublisher userEventPublisher, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userEventPublisher = userEventPublisher;
        this.passwordEncoder = passwordEncoder;
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

    public Optional<User> findByDocument(String document) {
        return Optional.ofNullable(userRepository.findByDocument(document));
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    public User createUser(User user) {
        validateNewUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userEventPublisher.publishUserCreatedEvent(user);
        return userRepository.save(user);
    }

    public User updateUser(UUID userId, User updatedUser) {
        User existingUser = getUserById(userId);
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        return userRepository.save(existingUser);
    }
    public void validateNewUser(User newUser) {
        if (findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (newUser.getDocument() != null && findByDocument(newUser.getDocument()).isPresent()) {
            throw new IllegalArgumentException("Document already registered");
        }
    }

    public User consultUserById(UUID id){
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with this ID does not exist"));
    }
}