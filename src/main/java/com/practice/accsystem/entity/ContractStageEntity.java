package com.practice.accsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность этапа договора
 */
@Entity
@Table(name = "contract_stage")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ContractStageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Основной договор, к которому относится этап
     */
    @ManyToOne(optional = false)
    private ContractEntity contract;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contractStage", orphanRemoval = true)
    private Set<ExpenseEntity> expenses = new HashSet<>();
}
