package com.practice.accsystem.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
