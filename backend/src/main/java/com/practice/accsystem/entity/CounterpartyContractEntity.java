package com.practice.accsystem.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Сущность договора с контрагентом
 */
@Entity
@Table(name = "counterparty_contract")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CounterpartyContractEntity implements ExcelRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Организация-контрагент, с которой заключается договор
     */
    @ManyToOne(optional = false)
    private CounterpartyEntity counterparty;

    /**
     * Основной договор, к которому относится договор с контрагентом
     */
    @ManyToOne(optional = false)
    private ContractEntity contract;

    /**
     * Название договора
     */
    private String title;

    /**
     * Тип договора
     */
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    /**
     * Сумма договора
     */
    private BigDecimal sum;

    /**
     * Плановый срок начала
     */
    @Temporal(TemporalType.DATE)
    private Date planStartDate;

    /**
     * Плановый срок конца
     */
    @Temporal(TemporalType.DATE)
    private Date planEndDate;

    /**
     * Фактический срок начала
     */
    @Temporal(TemporalType.DATE)
    private Date factStartDate;

    /**
     * Фактический срок конца
     */
    @Temporal(TemporalType.DATE)
    private Date factEndDate;
}
