package com.practice.accsystem.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

/**
 * Токен для обновления accessToken
 */
@Entity(name = "refreshtoken")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Пользователь, которому был выдан токен
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUserEntity user;

    /**
     * Данные токена
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * Дата, до которой токен действителен
     */
    @Column(nullable = false)
    private Instant expiryDate;
}
