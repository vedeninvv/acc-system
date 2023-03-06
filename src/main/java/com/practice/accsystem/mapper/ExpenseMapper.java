package com.practice.accsystem.mapper;

import com.practice.accsystem.dto.expense.ExpenseGetDto;
import com.practice.accsystem.dto.expense.ExpensePostDto;
import com.practice.accsystem.entity.ExpenseEntity;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    public ExpenseEntity toEntity(ExpensePostDto expensePostDto) {
        return ExpenseEntity.builder()
                .title(expensePostDto.getTitle())
                .planAmount(expensePostDto.getPlanAmount())
                .factAmount(expensePostDto.getFactAmount())
                .build();
    }

    public ExpenseGetDto toDto(ExpenseEntity expense) {
        return ExpenseGetDto.builder()
                .id(expense.getId())
                .contractStageId(expense.getContractStage().getId())
                .title(expense.getTitle())
                .planAmount(expense.getPlanAmount())
                .factAmount(expense.getFactAmount())
                .build();
    }
}
