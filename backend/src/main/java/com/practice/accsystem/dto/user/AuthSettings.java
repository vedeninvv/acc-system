package com.practice.accsystem.dto.user;

import com.practice.accsystem.entity.user.Role;
import lombok.Data;

import java.util.Date;

/**
 * DTO для изменения настроек авторизации
 */
@Data
public class AuthSettings {
    /**
     * Роль пользователя
     */
    private Role role;

    /**
     * Дата прекращения действия аккаунта
     */
    private Date dateUserExpired;
}
