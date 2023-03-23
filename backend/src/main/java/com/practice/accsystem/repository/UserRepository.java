package com.practice.accsystem.repository;

import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Класс репозитория для пользователей
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<AppUserEntity, Long> {
    /**
     * Поиск по имени пользователя (логину)
     *
     * @param username имя пользователя (логин)
     * @return пользователь
     */
    Optional<AppUserEntity> findByUsername(String username);

    /**
     * Проверяет, существует ли пользователь с переданным именем пользователя (логином)
     *
     * @param username проверяемое имя пользователя (логин)
     * @return true, если существует, иначе false
     */
    Boolean existsByUsername(String username);

    /**
     * Поиск всех пользователей с фильтрацией по роли и совпадению переданной строки с хотя бы частью ФИО
     * Если переданные критерии null, то фильтрация по ним не осуществляется
     *
     * @param role      роль пользователя
     * @param searchStr строка, которая должна совпадать хотя бы с частью ФИО
     * @param pageable  настройки пагинации
     * @return пользователи
     */
    @Query("select user from AppUserEntity as user where " +
            "(:role is null or user.role = :role)" +
            " and (" +
            ":searchStr is null" +
            " or user.name like %:searchStr%" +
            " or user.surname like %:searchStr%" +
            " or user.middleName like %:searchStr%)" +
            " or user.username like %:searchStr%")
    Page<AppUserEntity> findAllByRoleAndSearchStr(Role role, String searchStr, Pageable pageable);
}
