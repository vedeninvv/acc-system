package com.practice.accsystem.repository;

import com.practice.accsystem.entity.user.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Класс репозитория для refresh JWT-токена
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    /**
     * Поиск по значению токена
     *
     * @param token значение токена
     * @return сущность токена
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Удалить токен по пользователю
     *
     * @param userId ID пользователя, чей токен нужно удалить
     */
    void deleteByUserId(Long userId);
}
