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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        user.setEmail("test@test.com");
    }

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    public void testCreateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");

        User createdUser = userService.createUser(user);

        Mockito.verify(userRepository).save(any(User.class));
        Mockito.verify(userEventPublisher).publishUserCreatedEvent(any(User.class));

        assertNotNull(createdUser);
        assertEquals(userId, createdUser.getId());
        assertEquals(user.getFullName(), createdUser.getFullName());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
    }

    @Test
    @DisplayName("Deve encontrar um usuário pelo ID com sucesso")
    public void testFindUserById_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = Optional.ofNullable(userService.getUserById(userId));

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
        assertEquals(user.getFullName(), foundUser.get().getFullName());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar usuário pelo ID")
    public void testFindUserById_UserNotFound() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

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
        updatedUser.setEmail("test@test.com");
        updatedUser.setPassword("newpassword123");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getFullName());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("newpassword123", result.getPassword());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar atualizar usuário não encontrado")
    public void testUpdateUser_UserNotFound() {
        User updatedUser = new User();
        updatedUser.setFullName("Jane Doe");
        updatedUser.setEmail("test@test.com");
        updatedUser.setPassword("newpassword123");

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar novo usuário e lançar exceção se o email já existir")
    public void validateNewUser_EmailAlreadyExists_ThrowsException() {
        User newUser = new User();
        newUser.setEmail("test@test.com");
        newUser.setDocument("12345678901");

        when(userRepository.findByEmail(any(String.class))).thenReturn(newUser);

        assertThrows(IllegalArgumentException.class, () -> userService.validateNewUser(newUser));
    }

    @Test
    @DisplayName("Deve validar novo usuário e lançar exceção se o documento já existir")
    public void validateNewUser_DocumentAlreadyExists_ThrowsException() {
        User newUser = new User();
        newUser.setEmail("test@test.com");
        newUser.setDocument("12345678901");

        when(userRepository.findByDocument(any(String.class))).thenReturn(newUser);

        assertThrows(IllegalArgumentException.class, () -> userService.validateNewUser(newUser));
    }

    @Test
    @DisplayName("Deve consultar usuário pelo ID e retornar usuário se ele existir")
    public void consultUserById_UserExists_ReturnsUser() {
        User user = new User();
        user.setId(userId);
        user.setEmail("test@test.com");
        user.setDocument("12345678901");

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));

        User foundUser = userService.consultUserById(userId);

        assertEquals(user, foundUser);
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar usuário pelo ID se ele não existir")
    public void consultUserById_UserDoesNotExist_ThrowsException() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.consultUserById(userId));
    }
}
