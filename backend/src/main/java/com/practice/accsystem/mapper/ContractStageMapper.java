package com.practice.accsystem.mapper;

import com.practice.accsystem.dto.contractStage.ContractStageGetDto;
import com.practice.accsystem.dto.contractStage.ContractStagePostDto;
import com.practice.accsystem.entity.ContractStageEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ContractStageMapper {
    private final ExpenseMapper expenseMapper;

    public ContractStageMapper(ExpenseMapper expenseMapper) {
        this.expenseMapper = expenseMapper;
    }

    public ContractStageEntity toEntity(ContractStagePostDto contractStagePostDto) {
        return ContractStageEntity.builder()
                .title(contractStagePostDto.getTitle())
                .sum(contractStagePostDto.getSum())
                .expenses(contractStagePostDto.getExpenses().stream()
                        .map(expenseMapper::toEntity).collect(Collectors.toSet()))
                .planStartDate(contractStagePostDto.getPlanStartDate())
                .planEndDate(contractStagePostDto.getPlanEndDate())
                .factStartDate(contractStagePostDto.getFactStartDate())
                .factEndDate(contractStagePostDto.getFactEndDate())
                .build();
    }

    public ContractStageGetDto toDto(ContractStageEntity contractStage) {
        return ContractStageGetDto.builder()
                .id(contractStage.getId())
                .contractId(contractStage.getContract().getId())
                .title(contractStage.getTitle())
                .sum(contractStage.getSum())
                .planTotalExpenses(contractStage.getPlanTotalExpenses())
                .factTotalExpenses(contractStage.getFactTotalExpenses())
                .expenses(contractStage.getExpenses().stream()
                        .map(expenseMapper::toDto).collect(Collectors.toSet()))
                .planStartDate(contractStage.getPlanStartDate())
                .planEndDate(contractStage.getPlanEndDate())
                .factStartDate(contractStage.getFactStartDate())
                .factEndDate(contractStage.getFactEndDate())
                .build();
    }
}
