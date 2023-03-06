package com.practice.accsystem.mapper;

import com.practice.accsystem.dto.contract.ContractGetDto;
import com.practice.accsystem.dto.contract.ContractPostDto;
import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ContractMapper {
    public ContractEntity toEntity(ContractPostDto contractPostDto) {
        return ContractEntity.builder()
                .title(contractPostDto.getTitle())
                .contractType(contractPostDto.getContractType())
                .sum(contractPostDto.getSum())
                .planStartDate(contractPostDto.getPlanStartDate())
                .planEndDate(contractPostDto.getPlanEndDate())
                .factStartDate(contractPostDto.getFactStartDate())
                .factEndDate(contractPostDto.getFactEndDate())
                .build();
    }

    public ContractGetDto toDto(ContractEntity contract) {
        return ContractGetDto.builder()
                .id(contract.getId())
                .assignedUserId(contract.getAssignedUser().getId())
                .title(contract.getTitle())
                .contractType(contract.getContractType())
                .sum(contract.getSum())
                .planStartDate(contract.getPlanStartDate())
                .planEndDate(contract.getPlanEndDate())
                .factStartDate(contract.getFactStartDate())
                .factEndDate(contract.getFactEndDate())
                .contractStagesMap(contract.getContractStages().stream()
                        .collect(Collectors.toMap(ContractStageEntity::getId, ContractStageEntity::getTitle)))
                .counterpartyContractsMap(contract.getCounterpartyContracts().stream()
                        .collect(Collectors.toMap(CounterpartyContractEntity::getId, CounterpartyContractEntity::getTitle)))
                .build();
    }
}
