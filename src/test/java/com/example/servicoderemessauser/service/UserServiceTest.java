package com.example.servicoderemessauser.service;

import com.example.servicoderemessauser.messaging.UserEventPublisher;
import com.example.servicoderemessauser.model.User;
import com.example.servicoderemessauser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserEventPublisher userEventPublisher;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UUID userId;
    private User user;

    @BeforeEach
    public void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        // Configure other fields as needed
    }

    @Test
    public void testCreateUser_Success() {
        // Mock behavior for userRepository.save
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Mock behavior for passwordEncoder
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");

        // Call the actual method being tested
        User createdUser = userService.createUser(user);

        // Verify that userRepository.save was called with the correct user object
        Mockito.verify(userRepository).save(Mockito.any(User.class));

        // Verify that userEventPublisher.sendUserCreatedEvent was called with the correct user object
        Mockito.verify(userEventPublisher).sendUserCreatedEvent(Mockito.any(User.class));

        assertNotNull(createdUser);
        assertEquals(userId, createdUser.getId());
        assertEquals("John Doe", createdUser.getFullName());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
    }

    @Test
    public void testFindUserById_Success() {
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = Optional.ofNullable(userService.getUserById(userId));

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
        assertEquals("John Doe", foundUser.get().getFullName());
    }

    @Test
    public void testFindUserById_UserNotFound() {
        // Mock do userRepository para retornar Optional.empty() quando findById for chamado
        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        // Tentativa de buscar um usuário com um UUID aleatório
        UUID nonExistingUserId = UUID.randomUUID();

        // Captura da exceção IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(nonExistingUserId);
        });

        // Verifica a mensagem da exceção
        assertEquals("User not found with id: " + nonExistingUserId, exception.getMessage());
    }


    @Test
    public void testUpdateUser_Success() {
        User updatedUser = new User();
        updatedUser.setFullName("Jane Doe");
        updatedUser.setEmail("jane.doe@example.com");
        updatedUser.setPassword("newpassword123");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getFullName());
        assertEquals("jane.doe@example.com", result.getEmail());
        assertEquals("newpassword123", result.getPassword());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        User updatedUser = new User();
        updatedUser.setFullName("Jane Doe");
        updatedUser.setEmail("jane.doe@example.com");
        updatedUser.setPassword("newpassword123");

        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());
    }
}
