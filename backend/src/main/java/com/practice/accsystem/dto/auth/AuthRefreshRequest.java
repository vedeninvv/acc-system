package com.practice.accsystem.dto.auth;

import lombok.Data;

/**
 * Класс на обновление токена
 */
@Data
public class AuthRefreshRequest {
    private String refreshToken;
}
