package com.practice.accsystem.dto.contract;

import com.practice.accsystem.entity.ContractType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractGetDto {
    private Long id;

    /**
     * Пользователь, управляющий контрактом
     */
    private Long assignedUserId;

    /**
     * Этапы договора
     */
    private Map<Long, String> contractStagesMap = new HashMap<>();

    /**
     * Договоры с контрагентами
     */
    private Map<Long, String> counterpartyContractsMap = new HashMap<>();

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
