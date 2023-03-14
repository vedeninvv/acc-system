package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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
     * @param contract контракт, к которому относятся контракты с контрагентами
     * @param pageable настройки пагинации
     * @return контракты с контрагентом
     */
    Page<CounterpartyContractEntity> findAllByContract(ContractEntity contract, Pageable pageable);

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
