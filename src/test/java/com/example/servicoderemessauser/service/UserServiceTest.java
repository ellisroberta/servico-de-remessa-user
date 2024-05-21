package com.example.servicoderemessauser.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.example.servicoderemessauser.fixture.BaseFixture;
import com.example.servicoderemessauser.fixture.model.UserFixture;
import com.example.servicoderemessauser.messaging.UserEventPublisher;
import com.example.servicoderemessauser.model.User;
import com.example.servicoderemessauser.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @BeforeAll
    public static void setUpFixture() {
        FixtureFactoryLoader.loadTemplates(BaseFixture.ALL.getPacote());
    }

    @BeforeEach
    public void setUp() {
        userId = UUID.randomUUID();
        user = Fixture.from(User.class).gimme(UserFixture.VALIDO);
        user.setId(userId);
    }

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    public void testCreateUser_Success() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");

        User createdUser = userService.createUser(user);

        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verify(userEventPublisher).publishUserCreatedEvent(Mockito.any(User.class));

        assertNotNull(createdUser);
        assertEquals(userId, createdUser.getId());
        assertEquals(user.getFullName(), createdUser.getFullName());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
    }

    @Test
    @DisplayName("Deve encontrar um usuário pelo ID com sucesso")
    public void testFindUserById_Success() {
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = Optional.ofNullable(userService.getUserById(userId));

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
        assertEquals(user.getFullName(), foundUser.get().getFullName());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar usuário pelo ID")
    public void testFindUserById_UserNotFound() {
        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        UUID nonExistingUserId = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(nonExistingUserId);
        });

        assertEquals("User not found with id: " + nonExistingUserId, exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar um usuário com sucesso")
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
    @DisplayName("Deve lançar exceção quando tentar atualizar usuário não encontrado")
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
