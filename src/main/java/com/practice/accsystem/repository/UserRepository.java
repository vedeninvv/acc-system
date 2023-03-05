package com.practice.accsystem.repository;

import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<AppUserEntity, Long> {
    Optional<AppUserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
}
