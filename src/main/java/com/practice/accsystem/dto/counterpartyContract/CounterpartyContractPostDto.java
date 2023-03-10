package com.practice.accsystem.dto.counterpartyContract;

import com.practice.accsystem.entity.ContractType;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO для ввода данных контракта с контрагентом с клиента
 */
@Data
public class CounterpartyContractPostDto {
    /**
     * Организация-контрагент, с которой заключается договор
     */
    @NotNull
    private Long counterpartyId;

    /**
     * Название договора
     */
    @NotBlank
    @Size(max = 255)
    private String title;

    /**
     * Тип договора
     */
    @NotNull
    private ContractType contractType;

    /**
     * Сумма договора
     */
    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal sum;

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