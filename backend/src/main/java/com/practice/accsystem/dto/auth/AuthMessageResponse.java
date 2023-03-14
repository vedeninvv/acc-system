package com.practice.accsystem.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс текстового сообщения о статусе авторизации
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthMessageResponse {
    private String message;
}
