package com.practice.accsystem.dto.contract;

import com.practice.accsystem.entity.ContractType;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractPostDto {
    /**
     * Название договора
     */
    @NotBlank
    @Size(max = 255)
    private String title;

    /**
     * Тип договора
     */
    @NotNull
    private ContractType contractType;

    /**
     * Сумма договора
     */
    @DecimalMin(value = "0.0")
    @Digits(integer = 12, fraction = 2)
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
