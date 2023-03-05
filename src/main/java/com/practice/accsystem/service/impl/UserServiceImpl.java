package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import com.practice.accsystem.exception.DuplicateUniqueValueException;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.exception.RelatedEntitiesCanNotBeDeleted;
import com.practice.accsystem.repository.UserRepository;
import com.practice.accsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public AppUserEntity createUser(AppUserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUniqueValueException(String.format("Username '%s' is already taken", user.getUsername()));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @Override
    public AppUserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String.format("User not found by id '%d'", userId)));
    }

    @Override
    public Page<AppUserEntity> findAllUsers(Role role, String searchStr, Pageable pageable) {
        if (searchStr != null) {
            searchStr = searchStr.toLowerCase(Locale.ROOT);
        }
        return userRepository.findAllByRoleAndSearchStr(role, searchStr, pageable);
    }

    @Override
    public AppUserEntity updateUser(Long userId, AppUserEntity newUser) {
        AppUserEntity oldUser = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String.format("User not found by id '%d' when try to update", userId)));

        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new DuplicateUniqueValueException(
                    String.format("Username '%s' already exist when try to update user by id '%d''", newUser.getUsername(), userId));
        }

        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(encoder.encode(newUser.getPassword()));
        oldUser.setName(newUser.getName());
        oldUser.setSurname(newUser.getSurname());
        oldUser.setMiddleName(newUser.getMiddleName());

        return userRepository.save(oldUser);
    }

    @Override
    public AppUserEntity deleteUser(Long userId) {
        AppUserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String.format("User not found by id '%d' when try to delete", userId)));

        if (!user.getManagingContracts().isEmpty()) {
            throw new RelatedEntitiesCanNotBeDeleted("User", "Contract");
        }

        userRepository.delete(user);
        return user;
    }
}
