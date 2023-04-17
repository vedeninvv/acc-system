package com.practice.accsystem.mapper;

import com.practice.accsystem.dto.counterparty.CounterpartyGetDto;
import com.practice.accsystem.dto.counterparty.CounterpartyPostDto;
import com.practice.accsystem.entity.CounterpartyEntity;
import org.springframework.stereotype.Component;

@Component
public class CounterpartyMapper {
    public CounterpartyGetDto toDto(CounterpartyEntity counterparty) {
        return CounterpartyGetDto.builder()
                .id(counterparty.getId())
                .title(counterparty.getTitle())
                .address(counterparty.getAddress())
                .INN(counterparty.getINN())
                .build();
    }

    public CounterpartyEntity toEntity(CounterpartyPostDto counterpartyPostDto) {
        return CounterpartyEntity.builder()
                .title(counterpartyPostDto.getTitle())
                .address(counterpartyPostDto.getAddress())
                .INN(counterpartyPostDto.getINN())
                .build();
    }
}
