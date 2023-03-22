package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Класс репозитория для контракта с контрагентами
 */
@Repository
public interface CounterpartyContractRepository extends PagingAndSortingRepository<CounterpartyContractEntity, Long> {
    /**
     * Поиск контракта с контрагентами по ID среди тех, что относятся к переданному контракту
     *
     * @param contract               контракт, к которому должен относиться контракт с контрагентами
     * @param counterpartyContractId ID контракта с контрагентом
     * @return контракт с контрагентом
     */
    Optional<CounterpartyContractEntity> findByContractAndId(ContractEntity contract, Long counterpartyContractId);

    /**
     * Поиск всех контрактов с контрагентами по переданному контракту
     *
     * @param contract       контракт, к которому относятся контракты с контрагентами
     * @param counterpartyId ID организации-контрагента, договора с которой ищутся
     * @param title          название или его часть
     * @param minSum         минимальная сумма этапа
     * @param maxSum         максимальная сумма этапа
     * @param startPeriod    начальная дата для планируемой или фактической даты
     * @param endPeriod      конечная дата для планируемой или фактической даты
     * @param pageable       настройки пагинации
     * @return контракты с контрагентом
     */
    @Query("select counterpartyContract from CounterpartyContractEntity as counterpartyContract where" +
            " counterpartyContract.contract = :contract" +
            " and (:counterpartyId is null or counterpartyContract.counterparty.id = :counterpartyId)" +
            " and (:title is null or counterpartyContract.title like %:title%)" +
            " and (:minSum is null or counterpartyContract.sum >= :minSum)" +
            " and (:maxSum is null or counterpartyContract.sum <= :maxSum)" +
            " and (:startPeriod is null or counterpartyContract.planStartDate >= :startPeriod" +
            " or counterpartyContract.factStartDate >= :startPeriod)" +
            " and (:endPeriod is null or counterpartyContract.planEndDate <= :endPeriod" +
            " or counterpartyContract.factEndDate <= :endPeriod)")
    Page<CounterpartyContractEntity> findAllByContract(ContractEntity contract, Long counterpartyId, String title,
                                                       BigDecimal minSum, BigDecimal maxSum, Date startPeriod, Date endPeriod, Pageable pageable);

    /**
     * Поиск всех контрактов с контрагентами, относящихся к переданному пользователю и входящих в переданный период
     * по планируемым датам сроков
     *
     * @param user        пользователь, к которому должны относиться контракты
     * @param periodStart дата начала периода планируемых сроков
     * @param periodEnd   дата конца периода планируемых сроков
     * @return список контрактов с контрагентами
     */
    @Query("select distinct counterpartyContract from CounterpartyContractEntity as counterpartyContract " +
            "join counterpartyContract.contract as contract where " +
            "contract.assignedUser = :user " +
            "and counterpartyContract.planStartDate between :periodStart and :periodEnd " +
            "and counterpartyContract.planEndDate between :periodStart and :periodEnd")
    List<CounterpartyContractEntity> findAllByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd);
}
