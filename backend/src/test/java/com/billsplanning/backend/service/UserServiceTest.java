package com.billsplanning.backend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.billsplanning.backend.entity.User;
import com.billsplanning.backend.exeption.EmailAlreadyExistsException;
import com.billsplanning.backend.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should save user successfully with encrypted password")
    void shouldSaveUserWithEncryptedPassword() {
        User userInput = new User();
        userInput.setUsername("test");
        userInput.setEmail("test@test.com");
        userInput.setPassword("123");

        when(passwordEncoder.encode("123")).thenReturn("123456");
        
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.registerUser(userInput);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("test");
        assertThat(savedUser.getPassword()).isEqualTo("123456");
    }

    @Test
    @DisplayName("Should fail to save user with duplicate email")
    void shouldFailToSaveUser() {
        User userInput = new User();
        userInput.setEmail("test@test.com");
        userInput.setPassword("123456");
        
        when(userRepository.existsByEmail("test@test.com"))
            .thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.registerUser(userInput);
        });

        assertThat(exception.getMessage()).isEqualTo("e-mail test@test.com already exists");
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
    
}
