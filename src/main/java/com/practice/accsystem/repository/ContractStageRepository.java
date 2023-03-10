package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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
     * Поиск всех этапов переданного контракта
     *
     * @param contract контракт, чьи этапы ищутся
     * @param pageable настройки пагинации
     * @return этапы контракта
     */
    Page<ContractStageEntity> findAllByContract(ContractEntity contract, Pageable pageable);
}
