package com.practice.accsystem.service;

import com.practice.accsystem.dto.user.AuthSettings;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import com.practice.accsystem.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис для пользователей
 */
public interface UserService {
    /**
     * Создать пользователя
     *
     * @param user данные для создания пользователя
     * @return созданный пользователь
     */
    AppUserEntity createUser(AppUserEntity user);

    /**
     * Найти пользователя по ID
     *
     * @param userId ID пользователя
     * @return пользователь
     */
    AppUserEntity findUserById(Long userId);

    /**
     * Найти всех пользователей с фильтрацией по роли и совпадению переданной строки с хотя бы частью ФИО
     * Если переданные критерии null, то фильтрация по ним не осуществляется
     *
     * @param role      роль пользователя
     * @param searchStr строка, которая должна совпадать хотя бы с частью ФИО
     * @param pageable  настройки пагинации
     * @return пользователи
     */
    Page<AppUserEntity> findAllUsers(Role role, String searchStr, Pageable pageable);

    /**
     * Обновить пользователя
     *
     * @param oldUser пользователь, которого нужно обновить
     * @param newUser данные для обновления пользователя
     * @return обновленный пользователь
     */
    AppUserEntity updateUser(AppUserEntity oldUser, AppUserEntity newUser);

    /**
     * Удалить пользователя
     * Удаление невозможно, если существуют контракты, которые относятся к удаляемому пользователю
     *
     * @param user удаляемый пользователь
     * @return удаленный пользователь
     */
    AppUserEntity deleteUser(AppUserEntity user);

    /**
     * Сделать запись о входе пользователя в no-sql базе
     *
     * @param userDetails пользователь, который входит
     */
    void createLoginHistoryRecord(UserDetailsImpl userDetails);

    /**
     * Обновить настройки авторизации (роль и дату действия) пользователя
     *
     * @param user         обновляемый пользователь
     * @param authSettings настройки авторизации
     * @return обновленный пользователь
     */
    AppUserEntity updateUserAuthSettings(AppUserEntity user, AuthSettings authSettings);
}
