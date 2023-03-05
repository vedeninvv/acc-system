package com.practice.accsystem.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String name;

    /**
     * Фамилия пользователя
     */
    @NotBlank
    private String surname;

    /**
     * Отчество пользователя
     */
    @NotBlank
    private String middleName;
}
