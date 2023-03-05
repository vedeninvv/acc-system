package com.practice.accsystem.dto.counterparty;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
