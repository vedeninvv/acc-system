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

@Repository
public interface CounterpartyContractRepository extends PagingAndSortingRepository<CounterpartyContractEntity, Long> {
    Optional<CounterpartyContractEntity> findByContractAndId(ContractEntity contract, Long counterpartyContractId);

    Page<CounterpartyContractEntity> findAllByContract(ContractEntity contract, Pageable pageable);

    @Query("select distinct counterpartyContract from CounterpartyContractEntity as counterpartyContract " +
            "join counterpartyContract.contract as contract where " +
            "contract.assignedUser = :user " +
            "and counterpartyContract.planStartDate between :periodStart and :periodEnd " +
            "and counterpartyContract.planEndDate between :periodStart and :periodEnd")
    List<CounterpartyContractEntity> findAllByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd);
}
