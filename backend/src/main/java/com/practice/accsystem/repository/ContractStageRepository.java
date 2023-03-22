package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * Класс репозитория для этапа контракта
 */
@Repository
public interface ContractStageRepository extends PagingAndSortingRepository<ContractStageEntity, Long> {
    /**
     * Поиск этапа по ID среди всех этапов контракта
     *
     * @param contract        контракт, среди чьих этапов осуществляется поиск
     * @param contractStageId ID этапа контракта
     * @return этап контракта
     */
    Optional<ContractStageEntity> findByContractAndId(ContractEntity contract, Long contractStageId);

    /**
     * Поиск всех этапов переданного контракта, которые удовлетворяют фильтрам
     *
     * @param contract    контракт, чьи этапы ищутся
     * @param pageable    настройки пагинации
     * @param title       название или его часть
     * @param minSum      минимальная сумма этапа
     * @param maxSum      максимальная сумма этапа
     * @param startPeriod начальная дата для планируемой или фактической даты
     * @param endPeriod   конечная дата для планируемой или фактической даты
     * @return этапы контракта
     */
    @Query("select contractStage from ContractStageEntity as contractStage where" +
            " contractStage.contract = :contract" +
            " and (:title is null or contractStage.title like %:title%)" +
            " and (:minSum is null or contractStage.sum >= :minSum)" +
            " and (:maxSum is null or contractStage.sum <= :maxSum)" +
            " and (:startPeriod is null or contractStage.planStartDate >= :startPeriod" +
            " or contractStage.factStartDate >= :startPeriod)" +
            " and (:endPeriod is null or contractStage.planEndDate <= :endPeriod" +
            " or contractStage.factEndDate <= :endPeriod)")
    Page<ContractStageEntity> findAllByContract(ContractEntity contract, String title, BigDecimal minSum,
                                                BigDecimal maxSum, Date startPeriod, Date endPeriod, Pageable pageable);
}
