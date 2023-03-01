package com.practice.accsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
public class CounterpartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "counterparty")
    private Set<CounterpartyContractEntity> counterpartyContracts = new HashSet<>();

    /**
     * Название контрагента
     */
    private String title;

    /**
     * Адрес контрагента
     */
    private String address;

    /**
     * ИНН контрагента
     */
    private String INN;
}
