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
public class ContractEntity implements ExcelRecord {
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
    @Builder.Default
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "contract", orphanRemoval = true)
    private Set<ContractStageEntity> contractStages = new HashSet<>();

    /**
     * Договоры с контрагентами
     */
    @Builder.Default
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
