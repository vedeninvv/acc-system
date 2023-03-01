package com.practice.accsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Сущность пункта расходов этапа договора
 */
@Entity
@Table(name = "expense")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Этап договора, к которому относится пункт расходов
     */
    @ManyToOne(optional = false)
    private ContractStageEntity contractStage;

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
