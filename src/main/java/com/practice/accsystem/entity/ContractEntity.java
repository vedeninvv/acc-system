package com.practice.accsystem.entity;

import com.practice.accsystem.entity.user.AppUserEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность основного договора
 */
@Entity
@Table(name = "contract")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Пользователь, управляющий контрактом
     */
    @ManyToOne(optional = false)
    private AppUserEntity assignedUser;

    /**
     * Этапы договора
     */
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "contract", orphanRemoval = true)
    private Set<ContractStageEntity> contractStages = new HashSet<>();

    /**
     * Договоры с контрагентами
     */
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "contract", orphanRemoval = true)
    private Set<CounterpartyContractEntity> counterpartyContracts = new HashSet<>();

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
