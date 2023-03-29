package com.practice.accsystem.repository;

import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private AppUserEntity user1;
    private AppUserEntity user2;
    private AppUserEntity user3;
    private AppUserEntity user4;

    @BeforeEach
    void prepareUsers() {
        user1 = AppUserEntity.builder()
                .username("user1 *общее*")
                .role(Role.USER)
                .name("Имя пользователя1")
                .surname("Фамилия пользователя1")
                .middleName("Отчество пользователя1")
                .build();
        user2 = AppUserEntity.builder()
                .username("user2")
                .role(Role.ADMIN)
                .name("Имя пользователя2 *общее*")
                .surname("Фамилия пользователя2")
                .middleName("Отчество пользователя2")
                .build();
        user3 = AppUserEntity.builder()
                .username("username3")
                .role(Role.USER)
                .name("Имя3")
                .surname("Фамилия3 *общее*")
                .middleName("Отчество3")
                .build();
        user4 = AppUserEntity.builder()
                .username("login4")
                .role(Role.ADMIN)
                .name("Name4")
                .surname("Surname4")
                .middleName("MiddleName4 *общее*")
                .build();
        userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));
    }

    @Test
    void testFindAllByRoleAndSearchStrWithoutFilters() {
        List<AppUserEntity> expectedUsers = Arrays.asList(user1, user2, user3, user4);

        Page<AppUserEntity> actualUsers = userRepository.findAllByRoleAndSearchStr(null, null, PageRequest.of(0, 10));

        assertThat(actualUsers).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedUsers);
    }

    @Test
    void testFindAllByRoleAndSearchStrWhenRoleUser() {
        List<AppUserEntity> expectedUsers = Arrays.asList(user1, user3);

        Page<AppUserEntity> actualUsers = userRepository.findAllByRoleAndSearchStr(Role.USER, null, PageRequest.of(0, 10));

        System.out.println(actualUsers.getContent());
        assertThat(actualUsers).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedUsers);
    }

    @Test
    void testFindAllByRoleAndSearchStrWhenSearchStrIsEqualToName() {
        List<AppUserEntity> expectedUsers = Collections.singletonList(user1);

        Page<AppUserEntity> actualUsers = userRepository.findAllByRoleAndSearchStr(null, "user1", PageRequest.of(0, 10));

        assertThat(actualUsers).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedUsers);
    }

    @Test
    void testFindAllByRoleAndSearchStrWhenSearchStrIsEqualToSurname() {
        List<AppUserEntity> expectedUsers = Collections.singletonList(user2);

        Page<AppUserEntity> actualUsers = userRepository.findAllByRoleAndSearchStr(
                null, "Фамилия пользователя2", PageRequest.of(0, 10));

        assertThat(actualUsers).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedUsers);
    }

    @Test
    void testFindAllByRoleAndSearchStrWhenSearchStrIsEqualToMiddleName() {
        List<AppUserEntity> expectedUsers = Collections.singletonList(user3);

        Page<AppUserEntity> actualUsers = userRepository.findAllByRoleAndSearchStr(null, "Отчество3", PageRequest.of(0, 10));

        assertThat(actualUsers).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedUsers);
    }

    @Test
    void testFindAllByRoleAndSearchStrWhenSearchStrIsEqualToUsername() {
        List<AppUserEntity> expectedUsers = Collections.singletonList(user4);

        Page<AppUserEntity> actualUsers = userRepository.findAllByRoleAndSearchStr(null, "login4", PageRequest.of(0, 10));

        assertThat(actualUsers).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedUsers);
    }

    @Test
    void testFindAllByRoleAndSearchStrWhenSearchStrIsEqualToAll() {
        List<AppUserEntity> expectedUsers = Arrays.asList(user1, user2, user3, user4);

        Page<AppUserEntity> actualUsers = userRepository.findAllByRoleAndSearchStr(null, "общее", PageRequest.of(0, 10));

        assertThat(actualUsers).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedUsers);
    }


}
