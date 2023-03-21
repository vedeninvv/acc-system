package com.practice.accsystem.entity.user;

import com.practice.accsystem.entity.ContractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность пользователя
 */
@Entity
@Table(name = "appuser")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип роли
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Логин пользователя
     */
    @Column(unique = true)
    private String username;

    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Контракты, управляемые пользователем
     */
    @Builder.Default
    @OneToMany(mappedBy = "assignedUser")
    private Set<ContractEntity> managingContracts = new HashSet<>();

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
     * Дата прекращения действия аккаунта пользователя
     */
    @Temporal(TemporalType.DATE)
    private Date dateUserExpired;
}
