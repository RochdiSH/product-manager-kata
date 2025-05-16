package com.alten.productmanagerkata.driven.security;

import com.alten.productmanagerkata.driven.persistence.entity.UserEntity;
import com.alten.productmanagerkata.driven.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        return User.builder()
                .username(entity.getEmail())
                .password(entity.getPassword())
                .roles(entity.getEmail().equals("admin@admin.com") ? "ADMIN" : "USER")
                .build();
    }
}
