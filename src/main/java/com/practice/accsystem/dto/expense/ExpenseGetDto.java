package com.practice.accsystem.dto.expense;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseGetDto {
    private Long id;

    /**
     * Этап договора, к которому относится пункт расходов
     */
    private Long contractStageId;

    /**
     * Название пункта расходов
     */
    private String title;

    /**
     * Планируемая величина пункта расходов
     */
    private BigDecimal planAmount;

    /**
     * Фактическая величина пункта расходов
     */
    private BigDecimal factAmount;
}
