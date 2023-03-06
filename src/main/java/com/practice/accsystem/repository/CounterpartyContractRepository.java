package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CounterpartyContractRepository extends PagingAndSortingRepository<CounterpartyContractEntity, Long> {
    Optional<CounterpartyContractEntity> findByContractAndId(ContractEntity contract, Long counterpartyContractId);

    Page<CounterpartyContractEntity> findAllByContract(ContractEntity contract, Pageable pageable);
}
