package com.alten.productmanagerkata.domain.service.impl;

import com.alten.productmanagerkata.domain.exception.UserAlreadyExistsException;
import com.alten.productmanagerkata.domain.model.User;
import com.alten.productmanagerkata.domain.service.UserService;
import com.alten.productmanagerkata.driven.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    private User sampleUser(String email) {
        return User.builder()
                .email(email)
                .username("user")
                .firstname("first")
                .password("hashed")
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        User user = sampleUser("test@example.com");
        User saved = userService.register(user);

        assertNotNull(saved.getId());
        assertEquals("test@example.com", saved.getEmail());
    }

    @Test
    void shouldThrowIfEmailAlreadyExists() {
        User user = sampleUser("dup@example.com");
        userService.register(user);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(sampleUser("dup@example.com"));
        });
    }

    @Test
    void shouldFindUserByEmail() {
        User user = sampleUser("findme@example.com");
        userService.register(user);

        Optional<User> found = userService.findByEmail("findme@example.com");
        assertTrue(found.isPresent());
        assertEquals("findme@example.com", found.get().getEmail());
    }

    @Test
    void shouldReturnEmptyIfEmailNotFound() {
        Optional<User> found = userService.findByEmail("absent@example.com");
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindUserById() {
        User user = userService.register(sampleUser("iduser@example.com"));
        Optional<User> found = userService.findById(user.getId());

        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getId());
    }

    @Test
    void shouldReturnEmptyIfIdNotFound() {
        Optional<User> found = userService.findById(9999L);
        assertTrue(found.isEmpty());
    }
}
