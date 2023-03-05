package com.practice.accsystem.mapper;

import com.practice.accsystem.dto.counterparty.CounterpartyGetDto;
import com.practice.accsystem.dto.counterparty.CounterpartyPostDto;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.CounterpartyEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class CounterpartyMapper {
    public CounterpartyGetDto toDto(CounterpartyEntity counterparty) {
        return CounterpartyGetDto.builder()
                .id(counterparty.getId())
                .title(counterparty.getTitle())
                .address(counterparty.getAddress())
                .INN(counterparty.getINN())
                .counterpartyContractIds(counterparty.getCounterpartyContracts().stream()
                        .map(CounterpartyContractEntity::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    public CounterpartyEntity toEntity(CounterpartyPostDto counterpartyPostDto) {
        return CounterpartyEntity.builder()
                .title(counterpartyPostDto.getTitle())
                .address(counterpartyPostDto.getAddress())
                .INN(counterpartyPostDto.getINN())
                .counterpartyContracts(new HashSet<>())
                .build();
    }
}
