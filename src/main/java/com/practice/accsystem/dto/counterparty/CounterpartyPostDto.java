package com.practice.accsystem.dto.counterparty;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CounterpartyPostDto {
    /**
     * Название контрагента
     */
    @NotBlank
    @Size(max = 255)
    private String title;

    /**
     * Адрес контрагента
     */
    @NotBlank
    @Size(max = 255)
    private String address;

    /**
     * ИНН контрагента
     */
    @NotBlank
    @Size(max = 255)
    private String INN;
}
