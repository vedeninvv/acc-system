package com.practice.accsystem.dto.counterparty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
