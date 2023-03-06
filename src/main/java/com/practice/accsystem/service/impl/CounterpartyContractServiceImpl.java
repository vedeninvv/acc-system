package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.CounterpartyEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.repository.CounterpartyContractRepository;
import com.practice.accsystem.service.ContractService;
import com.practice.accsystem.service.CounterpartyContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CounterpartyContractServiceImpl implements CounterpartyContractService {
    private final CounterpartyContractRepository counterpartyContractRepository;
    private final ContractService contractService;

    public CounterpartyContractServiceImpl(CounterpartyContractRepository counterpartyContractRepository, ContractService contractService) {
        this.counterpartyContractRepository = counterpartyContractRepository;
        this.contractService = contractService;
    }

    @Override
    public CounterpartyContractEntity createCounterpartyContract(AppUserEntity user,
                                                                 ContractEntity contract,
                                                                 CounterpartyEntity counterparty,
                                                                 CounterpartyContractEntity counterpartyContract) {
        counterpartyContract.setContract(contract);
        counterpartyContract.setCounterparty(counterparty);

        return counterpartyContractRepository.save(counterpartyContract);
    }

    @Override
    public CounterpartyContractEntity findCounterpartyContractById(ContractEntity contract, Long counterpartyContractId) {
        return counterpartyContractRepository.findByContractAndId(contract, counterpartyContractId).orElseThrow(() ->
                new NotFoundEntityException(
                        String.format("counterpartyContract with id '%d' not found in contract with id '%d'",
                                counterpartyContractId, contract.getId())));
    }


    @Override
    public Page<CounterpartyContractEntity> findAllCounterpartyContractsByContract(ContractEntity contract, Pageable pageable) {
        return counterpartyContractRepository.findAllByContract(contract, pageable);
    }

    @Override
    public CounterpartyContractEntity updateCounterpartyContract(CounterpartyContractEntity oldCounterpartyContract,
                                                                 CounterpartyContractEntity newCounterpartyContract) {
        oldCounterpartyContract.setCounterparty(newCounterpartyContract.getCounterparty());
        oldCounterpartyContract.setTitle(newCounterpartyContract.getTitle());
        oldCounterpartyContract.setContractType(newCounterpartyContract.getContractType());
        oldCounterpartyContract.setSum(newCounterpartyContract.getSum());
        oldCounterpartyContract.setPlanStartDate(newCounterpartyContract.getPlanStartDate());
        oldCounterpartyContract.setPlanEndDate(newCounterpartyContract.getPlanEndDate());
        oldCounterpartyContract.setFactStartDate(newCounterpartyContract.getFactStartDate());
        oldCounterpartyContract.setFactEndDate(newCounterpartyContract.getFactEndDate());

        return oldCounterpartyContract;
    }

    @Override
    public CounterpartyContractEntity deleteCounterpartyContract(CounterpartyContractEntity counterpartyContract) {
        counterpartyContractRepository.delete(counterpartyContract);
        return counterpartyContract;
    }

    @Override
    public boolean hasAccessToCounterpartyContract(AppUserEntity user, CounterpartyContractEntity counterpartyContract) {
        return contractService.hasAccessToContract(user, counterpartyContract.getContract());
    }
}