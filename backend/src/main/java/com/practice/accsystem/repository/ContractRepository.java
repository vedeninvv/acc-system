package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
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
import java.util.Set;

/**
 * Класс репозитория для контрактов
 */
@Repository
public interface ContractRepository extends PagingAndSortingRepository<ContractEntity, Long> {
    /**
     * Получить все контракты с пагинацией и фильтрацией по переданным критериям
     * Если критерий null, то фильтрация по нему не осуществляется
     *
     * @param assignedUserId ID назначенного пользователя
     * @param title          название или его часть
     * @param contractType   тип контракта
     * @param minSum         минимальное значение для суммы контракта
     * @param maxSum         максимальное значение для суммы контракта
     * @param startDate      дата начала периода для плановой или фактической даты
     *                       (достаточно, чтобы в период входила хотя бы одна)
     * @param endDate        дата конца периода для плановой или фактической даты
     *                       (достаточно, чтобы в период входила хотя бы одна)
     * @param pageable       настройки пагинации
     * @return контракты, удовлетворяющие критериям
     */
    @Query("select contract from ContractEntity as contract where" +
            " (:assignedUserId is null or contract.assignedUser.id = :assignedUserId) " +
            " and (:title is null or contract.title like %:title%)" +
            " and (:contractType is null or contract.contractType = :contractType)" +
            " and (:minSum is null or contract.sum >= :minSum)" +
            " and (:maxSum is null  or contract.sum <= :maxSum)" +
            " and (" +
            "(:startDate is null and :endDate is null)" +
            " or (:startDate is not null and :endDate is null and (contract.planStartDate >= :startDate or contract.factStartDate >= :startDate))" +
            " or (:startDate is null and :endDate is not null and (contract.planEndDate <= :endDate or contract.factEndDate <= :endDate))" +
            " or (:startDate is not null and :endDate is not null and (" +
            "(contract.planStartDate between :startDate and :endDate and contract.planEndDate between :startDate and :endDate)" +
            " or (contract.factStartDate between :startDate and :endDate and contract.factEndDate between :startDate and :endDate)))" +
            ")")
    Page<ContractEntity> findAllWithFilters(Long assignedUserId,
                                            String title,
                                            ContractType contractType,
                                            BigDecimal minSum,
                                            BigDecimal maxSum,
                                            Date startDate,
                                            Date endDate,
                                            Pageable pageable);

    /**
     * Найти все контракты пользователя, планируемые сроки которого входят в заданный период
     *
     * @param user        назначенный на контракт пользователь, по которому происходит фильтрация
     * @param periodStart дата начала периода, в который должны входить планируемые сроки контракта
     * @param periodEnd   дата конца периода, в который должны входить планируемые сроки контракта
     * @return список контрактов
     */
    List<ContractEntity> findAllByAssignedUserAndPlanStartDateAfterAndPlanEndDateBefore(AppUserEntity user,
                                                                                        Date periodStart,
                                                                                        Date periodEnd);

    /**
     * Найти все контракты, которые имеют связь хотя бы с одним контрактом с контрагентами
     *
     * @param counterpartyContracts контракты с контрагентами, для которых ищутся контракты
     * @return контракты, у которых есть хоть один переданный контракт с контрагентом
     */
    Set<ContractEntity> findAllByCounterpartyContractsIn(Set<CounterpartyContractEntity> counterpartyContracts);
}
