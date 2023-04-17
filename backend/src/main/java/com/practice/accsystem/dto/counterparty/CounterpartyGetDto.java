package com.practice.accsystem.dto.counterparty;

import lombok.Builder;
import lombok.Data;

/**
 * DTO для вывода данных контрагента клиенту
 */
@Data
@Builder
public class CounterpartyGetDto {
    private Long id;

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
