package com.practice.accsystem.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO для ввода данных пользователя клиенту
 */
@Data
public class UserPostDto {
    /**
     * Логин пользователя
     */
    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    /**
     * Пароль пользователя
     */
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    /**
     * Имя пользователя
     */
    @NotBlank
    @Size(max = 255)
    private String name;

    /**
     * Фамилия пользователя
     */
    @NotBlank
    @Size(max = 255)
    private String surname;

    /**
     * Отчество пользователя
     */
    @NotBlank
    @Size(max = 255)
    private String middleName;
}
