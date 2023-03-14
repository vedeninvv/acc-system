package com.practice.accsystem.dto.auth;

import lombok.Data;

/**
 * Запрос на авторизацию пользователя
 */
@Data
public class AuthRequest {
    private String username;
    private String password;
}
