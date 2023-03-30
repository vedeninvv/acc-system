package com.practice.accsystem.dto.expense;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO для ввода данных расхода этапа контракта с клиента
 */
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
    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 19, fraction = 2)
    private BigDecimal planAmount;

    /**
     * Фактическая величина пункта расходов
     */
    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 19, fraction = 2)
    private BigDecimal factAmount;
}
