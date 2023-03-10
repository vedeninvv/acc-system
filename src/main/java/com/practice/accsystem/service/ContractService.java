package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ContractService {
    ContractEntity createContract(AppUserEntity user, ContractEntity contract);

    ContractEntity findContractById(Long contractId);

    Page<ContractEntity> findAllContracts(Long assignedUserId,
                                          String title,
                                          ContractType contractType,
                                          BigDecimal minSum,
                                          BigDecimal maxSum,
                                          Pageable pageable);

    List<ContractEntity> findAllContractsByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd);

    ContractEntity updateContract(ContractEntity oldContract, ContractEntity newContract);

    ContractEntity deleteContract(ContractEntity contract);

    boolean hasAccessToContract(AppUserEntity user, ContractEntity contract);

    ByteArrayResource createContractsReportByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd);

    ByteArrayResource createContractStageReport(ContractEntity contract);
}
