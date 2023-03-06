package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.CounterpartyEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CounterpartyContractService {
    CounterpartyContractEntity createCounterpartyContract(ContractEntity contract,
                                                          CounterpartyEntity counterparty,
                                                          CounterpartyContractEntity counterpartyContract);

    CounterpartyContractEntity findCounterpartyContractById(ContractEntity contract, Long counterpartyContractId);

    Page<CounterpartyContractEntity> findAllCounterpartyContractsByContract(ContractEntity contract, Pageable pageable);

    CounterpartyContractEntity updateCounterpartyContract(CounterpartyContractEntity oldCounterpartyContract,
                                                          CounterpartyContractEntity newCounterpartyContract);

    CounterpartyContractEntity deleteCounterpartyContract(CounterpartyContractEntity counterpartyContract);

    boolean hasAccessToCounterpartyContract(AppUserEntity user, CounterpartyContractEntity counterpartyContract);
}
