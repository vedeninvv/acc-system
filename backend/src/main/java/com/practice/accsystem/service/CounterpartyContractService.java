package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.CounterpartyEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Сервис для контрактов с контрагентами
 */
public interface CounterpartyContractService {
    /**
     * Создать контракт с контрагентом
     *
     * @param contract             контракт, к которому должен относиться контракт с контрагентом
     * @param counterparty         контрагент, с которым создается контракт
     * @param counterpartyContract данные для создания контракта с контрагентом
     * @return созданный контракт с контрагентом
     */
    CounterpartyContractEntity createCounterpartyContract(ContractEntity contract,
                                                          CounterpartyEntity counterparty,
                                                          CounterpartyContractEntity counterpartyContract);

    /**
     * Найти контракт с контрагентом по ID
     *
     * @param contract               контракт, к которому относится контракт с контрагентом по ID
     * @param counterpartyContractId ID контракта с контрагентом
     * @return контракт с контрагентом
     */
    CounterpartyContractEntity findCounterpartyContractById(ContractEntity contract, Long counterpartyContractId);

    /**
     * Найти все контракты с контрагентами по переданному контракту
     *
     * @param contract       контракт, к которому относятся контракты с контрагентами
     * @param counterpartyId ID организации-контрагента, договора с которой ищутся
     * @param title          название или его часть
     * @param contractType   тип контракта
     * @param minSum         минимальная сумма этапа
     * @param maxSum         максимальная сумма этапа
     * @param startPeriod    начальная дата для планируемой или фактической даты
     * @param endPeriod      конечная дата для планируемой или фактической даты
     * @param pageable       настройки пагинации
     * @return контракты с контрагентом
     */
    Page<CounterpartyContractEntity> findAllCounterpartyContractsByContract(ContractEntity contract,
                                                                            Long counterpartyId,
                                                                            String title,
                                                                            ContractType contractType,
                                                                            BigDecimal minSum,
                                                                            BigDecimal maxSum,
                                                                            Date startPeriod,
                                                                            Date endPeriod,
                                                                            Pageable pageable);

    /**
     * Найти все контракты с контрагентами, относящиеся к переданному пользователю и входящих в переданный период
     * по планируемым датам сроков
     *
     * @param user        пользователь, к которому должны относиться контракты
     * @param periodStart дата начала периода планируемых сроков
     * @param periodEnd   дата конца периода планируемых сроков
     * @return список контрактов с контрагентами
     */
    List<CounterpartyContractEntity> findAllCounterpartyContractsByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd);

    /**
     * Обновить контракт с контрагентом
     *
     * @param oldCounterpartyContract контракт с контрагентом, который нужно обновить
     * @param newCounterpartyContract данные для обновления контракта с контрагентом
     * @return обновленный контракт с контрагентом
     */
    CounterpartyContractEntity updateCounterpartyContract(CounterpartyContractEntity oldCounterpartyContract,
                                                          CounterpartyContractEntity newCounterpartyContract);

    /**
     * Удалить контракт с контрагентом
     *
     * @param counterpartyContract удаляемый контракт с контрагентом
     * @return удаленный контракт с контрагентами
     */
    CounterpartyContractEntity deleteCounterpartyContract(CounterpartyContractEntity counterpartyContract);

    /**
     * Проверить, может ли пользователь получить доступ к контракту с контрагентом и работать с ним
     *
     * @param user                 пользователь, который пытается получить доступ
     * @param counterpartyContract контракт с контрагентом, к которому получают доступ
     * @return true, если пользователь имеет доступ, иначе false
     */
    boolean hasAccessToCounterpartyContract(AppUserEntity user, CounterpartyContractEntity counterpartyContract);
}
