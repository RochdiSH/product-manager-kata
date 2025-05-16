package com.alten.productmanagerkata.domain.service.impl;

import com.alten.productmanagerkata.domain.exception.UserAlreadyExistsException;
import com.alten.productmanagerkata.domain.exception.UserNotFoundException;
import com.alten.productmanagerkata.domain.mapper.UserMapper;
import com.alten.productmanagerkata.domain.model.User;
import com.alten.productmanagerkata.domain.service.UserService;
import com.alten.productmanagerkata.driven.persistence.entity.UserEntity;
import com.alten.productmanagerkata.driven.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        UserEntity entity = userMapper.toEntity(user);
        Instant now = Instant.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        UserEntity saved = userRepository.save(entity);
        User result = userMapper.toDomain(saved);

        log.info("Utilisateur créé: {}", result.getEmail());
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(userMapper::toDomain);
    }
}
