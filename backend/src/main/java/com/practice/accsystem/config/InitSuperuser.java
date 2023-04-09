package com.practice.accsystem.config;

import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import com.practice.accsystem.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
public class InitSuperuser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitSuperuser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void createSuperuser() {
        if (!userRepository.existsByUsername(System.getenv("SUPERUSER_USERNAME"))) {
            userRepository.save(AppUserEntity.builder()
                    .id(1L)
                    .username(System.getenv("SUPERUSER_USERNAME"))
                    .password(passwordEncoder.encode(System.getenv("SUPERUSER_PASSWORD")))
                    .role(Role.ADMIN)
                    .dateUserExpired(null)
                    .name("Admin")
                    .surname("Admin")
                    .middleName("Admin")
                    .build());
        }
    }
}
