package com.practice.accsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
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
public class CounterpartyContractEntity {
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
    private BigInteger sum;

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
