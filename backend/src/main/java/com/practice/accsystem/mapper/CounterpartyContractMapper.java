package com.practice.accsystem.mapper;

import com.practice.accsystem.dto.counterpartyContract.CounterpartyContractGetDto;
import com.practice.accsystem.dto.counterpartyContract.CounterpartyContractPostDto;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.service.CounterpartyService;
import org.springframework.stereotype.Component;

@Component
public class CounterpartyContractMapper {
    private final CounterpartyService counterpartyService;
    private final CounterpartyMapper counterpartyMapper;

    public CounterpartyContractMapper(CounterpartyService counterpartyService, CounterpartyMapper counterpartyMapper) {
        this.counterpartyService = counterpartyService;
        this.counterpartyMapper = counterpartyMapper;
    }

    public CounterpartyContractEntity toEntity(CounterpartyContractPostDto counterpartyContractPostDto) {
        return CounterpartyContractEntity.builder()
                .title(counterpartyContractPostDto.getTitle())
                .contractType(counterpartyContractPostDto.getContractType())
                .sum(counterpartyContractPostDto.getSum())
                .counterparty(counterpartyService.findCounterpartyById(counterpartyContractPostDto.getCounterpartyId()))
                .planStartDate(counterpartyContractPostDto.getPlanStartDate())
                .planEndDate(counterpartyContractPostDto.getPlanEndDate())
                .factStartDate(counterpartyContractPostDto.getFactStartDate())
                .factEndDate(counterpartyContractPostDto.getFactEndDate())
                .build();
    }

    public CounterpartyContractGetDto toDto(CounterpartyContractEntity counterpartyContract) {
        return CounterpartyContractGetDto.builder()
                .id(counterpartyContract.getId())
                .counterparty(counterpartyMapper.toDto(counterpartyContract.getCounterparty()))
                .contractId(counterpartyContract.getContract().getId())
                .title(counterpartyContract.getTitle())
                .contractType(counterpartyContract.getContractType())
                .sum(counterpartyContract.getSum())
                .planStartDate(counterpartyContract.getPlanStartDate())
                .planEndDate(counterpartyContract.getPlanEndDate())
                .factStartDate(counterpartyContract.getFactStartDate())
                .factEndDate(counterpartyContract.getFactEndDate())
                .build();
    }
}
