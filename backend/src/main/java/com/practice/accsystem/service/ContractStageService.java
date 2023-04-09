package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Сервис для этапов контракта
 */
public interface ContractStageService {
    /**
     * Проверить, может ли пользователь получить доступ к этапу контракту и работать с ним
     *
     * @param user          пользователь, который пытается получить доступ
     * @param contractStage этап контракта, к которому получают доступ
     * @return true, если пользователь имеет доступ, иначе false
     */
    boolean hasAccessToContractStage(AppUserEntity user, ContractStageEntity contractStage);

    /**
     * Создать этап контракта
     *
     * @param contract      контракт, для которого создается этап
     * @param contractStage данные создаваемого этапа контракта
     * @return созданный этап контракта
     */
    ContractStageEntity createContractStage(ContractEntity contract, ContractStageEntity contractStage);

    /**
     * Найти этап контракта по ID
     *
     * @param contract        контракт, среди этапов которого осуществляется поиск
     * @param contractStageId ID этапа контракта
     * @return этап контракта
     */
    ContractStageEntity findContractStageById(ContractEntity contract, Long contractStageId);

    /**
     * Поиск всех этапов переданного контракта, которые удовлетворяют переданным фильтрам
     *
     * @param contract             контракт, чьи этапы ищутся
     * @param title                название или его часть
     * @param minSum               минимальная сумма этапа
     * @param maxSum               максимальная сумма этапа
     * @param minPlanTotalExpenses минимальная сумма плановых расходов
     * @param maxPlanTotalExpenses максимальная сумма плановых расходов
     * @param minFactTotalExpenses минимальная сумма фактических расходов
     * @param maxFactTotalExpenses максимальная сумма фактических расходов
     * @param startPeriod          начальная дата для планируемой или фактической даты
     * @param endPeriod            конечная дата для планируемой или фактической даты
     * @param pageable             настройки пагинации
     * @return этапы контракта
     */
    Page<ContractStageEntity> findAllContractStageByContract(ContractEntity contract,
                                                             String title,
                                                             BigDecimal minSum,
                                                             BigDecimal maxSum,
                                                             BigDecimal minPlanTotalExpenses,
                                                             BigDecimal maxPlanTotalExpenses,
                                                             BigDecimal minFactTotalExpenses,
                                                             BigDecimal maxFactTotalExpenses,
                                                             Date startPeriod,
                                                             Date endPeriod,
                                                             Pageable pageable);

    /**
     * Обновить этап контракта
     *
     * @param oldContractStage этап контракта, который нужно обновить
     * @param newContractStage данные этапа контракта для обновления
     * @return обновленный этап контракта
     */
    ContractStageEntity updateContractStage(ContractStageEntity oldContractStage, ContractStageEntity newContractStage);

    /**
     * Удалить этап контракта
     *
     * @param contractStage удаляемый этап контракта
     * @return удаленный этап контракта
     */
    ContractStageEntity deleteContractStage(ContractStageEntity contractStage);
}
