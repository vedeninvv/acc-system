package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import com.practice.accsystem.exception.DuplicateUniqueValueException;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.exception.RelatedEntitiesCanNotBeDeleted;
import com.practice.accsystem.repository.UserRepository;
import com.practice.accsystem.security.jwt.RefreshTokenService;
import com.practice.accsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUserWhenUsernameNotExist() {
        AppUserEntity user = AppUserEntity.builder()
                .username("user")
                .password("password")
                .build();
        AppUserEntity expectedUser = AppUserEntity.builder()
                .username("user")
                .password("encodedPassword")
                .build();
        when(userRepository.existsByUsername("user")).thenReturn(false);
        when(encoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(expectedUser);
        ArgumentCaptor<AppUserEntity> userCaptor = ArgumentCaptor.forClass(AppUserEntity.class);

        AppUserEntity actualUser = userService.createUser(user);

        verify(userRepository, times(1)).save(userCaptor.capture());
        AppUserEntity actualUserInArgsToSave = userCaptor.getValue();
        assertThat(actualUser).isEqualTo(expectedUser);
        assertThat(actualUserInArgsToSave).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    void testCreateUserWhenUsernameExist() {
        AppUserEntity user = AppUserEntity.builder()
                .username("user")
                .password("password")
                .build();
        when(userRepository.existsByUsername("user")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(DuplicateUniqueValueException.class);
    }

    @Test
    void testFindUserByExistingId() {
        AppUserEntity user = AppUserEntity.builder().id(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        AppUserEntity actualUser = userService.findUserById(1L);

        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void testFindUserByNotExistingId() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUserById(1L)).isInstanceOf(NotFoundEntityException.class);
    }

    @Test
    void testFindAllUsersWithoutFilters() {
        Page<AppUserEntity> users = new PageImpl<>(Arrays.asList(
                new AppUserEntity(),
                new AppUserEntity()
        ));
        when(userRepository.findAllByRoleAndSearchStr(null, null, PageRequest.of(1, 2)))
                .thenReturn(users);

        Page<AppUserEntity> actualUsers = userService.findAllUsers(null, null, PageRequest.of(1, 2));

        assertThat(actualUsers).isEqualTo(users);
    }

    @Test
    void testFindAllUsersWithAllFilters() {
        Page<AppUserEntity> users = new PageImpl<>(Arrays.asList(
                AppUserEntity.builder().username("name").role(Role.USER).build(),
                AppUserEntity.builder().name("name").role(Role.USER).build()
        ));
        when(userRepository.findAllByRoleAndSearchStr(Role.USER, "name", PageRequest.of(1, 2)))
                .thenReturn(users);

        Page<AppUserEntity> actualUsers = userService.findAllUsers(Role.USER, "name", PageRequest.of(1, 2));

        assertThat(actualUsers).isEqualTo(users);
    }

    @Test
    void testUpdateUserWhenUsernameNotExist() {
        Date currentDate = new Date();
        AppUserEntity user = AppUserEntity.builder()
                .id(1L)
                .username("username")
                .password("encoded_password")
                .role(Role.USER)
                .name("Петр")
                .surname("Петров")
                .middleName("Петрович")
                .dateUserExpired(currentDate)
                .build();
        AppUserEntity updatingContent = AppUserEntity.builder()
                .username("updated_username")
                .password("updated_password")
                .name("Обновленное имя")
                .surname("Обновленная фамилия")
                .middleName("Обновленное отчество")
                .build();
        AppUserEntity expectedUser = AppUserEntity.builder()
                .id(1L)
                .username("updated_username")
                .password("encoded_updated_password")
                .role(Role.USER)
                .name("Обновленное имя")
                .surname("Обновленная фамилия")
                .middleName("Обновленное отчество")
                .dateUserExpired(currentDate)
                .build();
        when(userRepository.existsByUsername("updated_username")).thenReturn(false);
        when(encoder.encode("updated_password")).thenReturn("encoded_updated_password");
        when(userRepository.save(user)).thenReturn(expectedUser);
        ArgumentCaptor<AppUserEntity> userCaptor = ArgumentCaptor.forClass(AppUserEntity.class);

        AppUserEntity actualUser = userService.updateUser(user, updatingContent);

        verify(userRepository, times(1)).save(userCaptor.capture());
        AppUserEntity actualUserInArgsToSave = userCaptor.getValue();
        assertThat(actualUser).isEqualTo(expectedUser);
        assertThat(actualUserInArgsToSave).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    void testUpdateUserWhenUsernameExist() {
        AppUserEntity user = AppUserEntity.builder().username("username").build();
        AppUserEntity updatingContent = AppUserEntity.builder().username("updated_username").build();
        when(userRepository.existsByUsername("updated_username")).thenReturn(true);

        assertThatThrownBy(() -> userService.updateUser(user, updatingContent)).isInstanceOf(DuplicateUniqueValueException.class);
    }

    @Test
    void testDeleteUserWhenHasRelatedContracts() {
        AppUserEntity user = AppUserEntity.builder()
                .id(1L)
                .username("username")
                .password("encoded_password")
                .role(Role.USER)
                .name("Петр")
                .surname("Петров")
                .middleName("Петрович")
                .managingContracts(new HashSet<>(Arrays.asList(
                        new ContractEntity(),
                        new ContractEntity()
                )))
                .build();

        assertThatThrownBy(() -> userService.deleteUser(user)).isInstanceOf(RelatedEntitiesCanNotBeDeleted.class);
    }

    @Test
    void testDeleteUserWhenNotHasRelatedContracts() {
        AppUserEntity user = AppUserEntity.builder()
                .id(1L)
                .username("username")
                .password("encoded_password")
                .role(Role.USER)
                .name("Петр")
                .surname("Петров")
                .middleName("Петрович")
                .build();

        AppUserEntity actualUser = userService.deleteUser(user);

        verify(refreshTokenService, times(1)).deleteByUserId(1L);
        verify(userRepository, times(1)).delete(user);
        assertThat(actualUser).isEqualTo(user);
    }
}
