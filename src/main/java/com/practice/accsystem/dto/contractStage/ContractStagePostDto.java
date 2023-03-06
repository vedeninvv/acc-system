package com.practice.accsystem.dto.contractStage;

import com.practice.accsystem.dto.expense.ExpensePostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @DecimalMin(value = "0.0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal sum;

    /**
     * Список расходов
     */
    private Set<ExpensePostDto> expenses;
}
