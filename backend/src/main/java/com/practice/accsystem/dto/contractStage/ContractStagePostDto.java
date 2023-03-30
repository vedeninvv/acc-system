package com.practice.accsystem.dto.contractStage;

import com.practice.accsystem.dto.expense.ExpensePostDto;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
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
    @Digits(integer = 19, fraction = 2)
    private BigDecimal sum;

    /**
     * Список расходов
     */
    @NotNull
    private Set<ExpensePostDto> expenses;

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
