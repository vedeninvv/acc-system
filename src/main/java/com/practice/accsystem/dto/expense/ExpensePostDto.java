package com.practice.accsystem.dto.expense;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ExpensePostDto {
    /**
     * Название пункта расходов
     */
    @NotBlank
    @Size(max = 255)
    private String title;

    /**
     * Планируемая величина пункта расходов
     */
    @DecimalMin(value = "0.0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal planAmount;

    /**
     * Фактическая величина пункта расходов
     */
    @DecimalMin(value = "0.0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal factAmount;
}
