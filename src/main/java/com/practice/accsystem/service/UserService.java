package com.practice.accsystem.service;

import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    AppUserEntity createUser(AppUserEntity user);

    AppUserEntity findUserById(Long userId);

    Page<AppUserEntity> findAllUsers(Role role, String searchStr, Pageable pageable);

    AppUserEntity updateUser(AppUserEntity oldUser, AppUserEntity newUser);

    AppUserEntity deleteUser(AppUserEntity user);
}
