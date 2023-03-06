package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractStageRepository extends PagingAndSortingRepository<ContractStageEntity, Long> {
    Optional<ContractStageEntity> findByContractAndId(ContractEntity contract, Long contractStageId);

    Page<ContractStageEntity> findAllByContract(ContractEntity contract, Pageable pageable);
}
