package com.practice.accsystem.mapper;

import com.practice.accsystem.dto.user.UserGetDto;
import com.practice.accsystem.dto.user.UserPostDto;
import com.practice.accsystem.dto.user.UserPutDto;
import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserGetDto toDto(AppUserEntity appUser) {
        return UserGetDto.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .role(appUser.getRole())
                .dateUserExpired(appUser.getDateUserExpired())
                .name(appUser.getName())
                .surname(appUser.getSurname())
                .middleName(appUser.getMiddleName())
                .managingContractsIds(appUser.getManagingContracts().stream()
                        .map(ContractEntity::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    public AppUserEntity toEntity(UserPostDto userPostDto) {
        return AppUserEntity.builder()
                .username(userPostDto.getUsername())
                .password(userPostDto.getPassword())
                .name(userPostDto.getName())
                .surname(userPostDto.getSurname())
                .middleName(userPostDto.getMiddleName())
                .dateUserExpired(userPostDto.getDateUserExpired())
                .role(userPostDto.getRole())
                .build();
    }

    public AppUserEntity toEntity(UserPutDto userPutDto) {
        return AppUserEntity.builder()
                .username(userPutDto.getUsername())
                .password(userPutDto.getPassword())
                .name(userPutDto.getName())
                .surname(userPutDto.getSurname())
                .middleName(userPutDto.getMiddleName())
                .build();
    }
}
