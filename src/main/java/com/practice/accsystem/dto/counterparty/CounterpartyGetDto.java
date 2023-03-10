package com.practice.accsystem.dto.counterparty;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * DTO для вывода данных контрагента клиенту
 */
@Data
@Builder
public class CounterpartyGetDto {
    private Long id;

    /**
     * ID контрактов, относящихся к организации-контрагенту
     */
    private Set<Long> counterpartyContractIds;

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
