package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ContractService {
    ContractEntity createContract(AppUserEntity user, ContractEntity contract);

    ContractEntity findContractById(Long contractId);

    Page<ContractEntity> findAllContracts(Long assignedUserId,
                                          String title,
                                          ContractType contractType,
                                          BigDecimal minSum,
                                          BigDecimal maxSum,
                                          Pageable pageable);

    ContractEntity updateContract(Long contractId, AppUserEntity user, ContractEntity newContract);

    ContractEntity deleteContract(AppUserEntity user, Long contractId);

    boolean hasAccessToContract(AppUserEntity user, ContractEntity contract);
}
