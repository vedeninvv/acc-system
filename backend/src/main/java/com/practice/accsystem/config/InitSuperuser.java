package com.practice.accsystem.config;

import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import com.practice.accsystem.repository.UserRepository;
import com.practice.accsystem.service.UserService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class InitSuperuser {
    private final UserService userService;
    private final UserRepository userRepository;

    public InitSuperuser(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void createSuperuser() {
        if (!userRepository.existsByUsername(System.getenv("SUPERUSER_USERNAME"))) {
            userService.createUser(AppUserEntity.builder()
                    .id(1L)
                    .username(System.getenv("SUPERUSER_USERNAME"))
                    .password(System.getenv("SUPERUSER_PASSWORD"))
                    .role(Role.ADMIN)
                    .dateUserExpired(null)
                    .name("Admin")
                    .surname("Admin")
                    .middleName("Admin")
                    .build());
        }
    }
}
