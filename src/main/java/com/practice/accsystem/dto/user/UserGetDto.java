package com.practice.accsystem.dto.user;

import com.practice.accsystem.entity.user.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class UserGetDto {
    private Long id;

    /**
     * Тип роли
     */
    private Role role;

    /**
     * Логин пользователя
     */
    private String username;

    /**
     * ID Контрактов, управляемых пользователем
     */
    private Set<Long> managingContractsIds;

    /**
     * Имя пользователя
     */
    private String name;

    /**
     * Фамилия пользователя
     */
    private String surname;

    /**
     * Отчество пользователя
     */
    private String middleName;

    /**
     * Дата прекращения действия
     */
    private Date dateUserExpired;

    public UserGetDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}