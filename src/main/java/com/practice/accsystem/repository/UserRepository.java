package com.practice.accsystem.repository;

import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<AppUserEntity, Long> {
    Optional<AppUserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("select user from AppUserEntity as user where " +
            ":role is null or user.role = :role" +
            " and (" +
            ":searchStr is null" +
            " or user.name like %:searchStr%" +
            " or user.surname like %:searchStr%" +
            " or user.middleName like %:searchStr%)")
    Page<AppUserEntity> findAllByRoleAndSearchStr(Role role, String searchStr, Pageable pageable);
}
