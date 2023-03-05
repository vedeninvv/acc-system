package com.practice.accsystem.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность организации-контрагента
 */
@Entity
@Table(name = "counterparty")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CounterpartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "counterparty")
    private Set<CounterpartyContractEntity> counterpartyContracts = new HashSet<>();

    /**
     * Название контрагента
     */
    @Column(unique = true)
    private String title;

    /**
     * Адрес контрагента
     */
    private String address;

    /**
     * ИНН контрагента
     */
    @Column(unique = true)
    private String INN;
}
