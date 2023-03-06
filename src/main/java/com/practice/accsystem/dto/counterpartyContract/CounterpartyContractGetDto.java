package com.practice.accsystem.dto.counterpartyContract;

import com.practice.accsystem.entity.ContractType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class CounterpartyContractGetDto {
    private Long id;

    /**
     * Организация-контрагент, с которой заключается договор
     */
    private Long counterpartyId;

    /**
     * Основной договор, к которому относится договор с контрагентом
     */
    private Long contractId;

    /**
     * Название договора
     */
    private String title;

    /**
     * Тип договора
     */
    private ContractType contractType;

    /**
     * Сумма договора
     */
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
