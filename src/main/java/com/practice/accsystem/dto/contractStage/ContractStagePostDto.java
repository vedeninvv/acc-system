package com.practice.accsystem.dto.contractStage;

import com.practice.accsystem.dto.expense.ExpensePostDto;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO для ввода данных этапа контракта с клиента
 */
@Data
public class ContractStagePostDto {
    /**
     * Название этапа
     */
    @NotBlank
    @Size(max = 255)
    private String title;

    /**
     * Сумма этапа
     */
    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal sum;

    /**
     * Список расходов
     */
    @NotNull
    private Set<ExpensePostDto> expenses;
}
