package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.LoginHistory;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import com.practice.accsystem.exception.DuplicateUniqueValueException;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.exception.RelatedEntitiesCanNotBeDeleted;
import com.practice.accsystem.repository.UserRepository;
import com.practice.accsystem.repository.mongo.LoginHistoryRepository;
import com.practice.accsystem.security.UserDetailsImpl;
import com.practice.accsystem.security.jwt.RefreshTokenService;
import com.practice.accsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder encoder;
    private final LoginHistoryRepository loginHistoryRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RefreshTokenService refreshTokenService,
                           PasswordEncoder encoder,
                           LoginHistoryRepository loginHistoryRepository) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.encoder = encoder;
        this.loginHistoryRepository = loginHistoryRepository;
    }

    @Override
    public AppUserEntity createUser(AppUserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUniqueValueException(String.format("Username '%s' is already taken", user.getUsername()));
        }

        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public AppUserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String.format("User not found by id '%d'", userId)));
    }

    @Override
    public Page<AppUserEntity> findAllUsers(Role role, String searchStr, Pageable pageable) {
        return userRepository.findAllByRoleAndSearchStr(role, searchStr, pageable);
    }

    @Override
    public AppUserEntity updateUser(AppUserEntity oldUser, AppUserEntity newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new DuplicateUniqueValueException(
                    String.format("Username '%s' already exist when try to update user by id '%d''", newUser.getUsername(), oldUser.getId()));
        }

        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(encoder.encode(newUser.getPassword()));
        oldUser.setName(newUser.getName());
        oldUser.setSurname(newUser.getSurname());
        oldUser.setMiddleName(newUser.getMiddleName());

        return userRepository.save(oldUser);
    }

    @Override
    public AppUserEntity deleteUser(AppUserEntity user) {
        if (!user.getManagingContracts().isEmpty()) {
            throw new RelatedEntitiesCanNotBeDeleted("User", "Contract");
        }

        refreshTokenService.deleteByUserId(user.getId());
        userRepository.delete(user);
        return user;
    }

    @Override
    public void createLoginHistoryRecord(UserDetailsImpl userDetails) {
        loginHistoryRepository.save(
                new LoginHistory(
                        userDetails.getUsername(),
                        userDetails.getId(),
                        new Date())
        );
    }
}
