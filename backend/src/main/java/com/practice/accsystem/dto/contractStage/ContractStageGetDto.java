package com.practice.accsystem.dto.contractStage;

import com.practice.accsystem.dto.expense.ExpenseGetDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * DTO для вывода данных этапа контракта клиенту
 */
@Data
@Builder
public class ContractStageGetDto {
    private Long id;

    /**
     * Основной договор, к которому относится этап
     */
    private Long contractId;

    /**
     * Название этапа
     */
    private String title;

    /**
     * Сумма этапа
     */
    private BigDecimal sum;

    /**
     * Сумма планируемых расходов
     */
    private BigDecimal planTotalExpenses;

    /**
     * Сумма фактических расходов
     */
    private BigDecimal factTotalExpenses;

    /**
     * Список расходов
     */
    private Set<ExpenseGetDto> expenses;

    /**
     * Плановый срок начала
     */
    private Date planStartDate;

    /**
     * Плановый срок конца
     */
    private Date planEndDate;

    /**
     * Фактический срок начала
     */
    private Date factStartDate;

    /**
     * Фактический срок конца
     */
    private Date factEndDate;
}
