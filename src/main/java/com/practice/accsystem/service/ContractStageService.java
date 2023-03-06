package com.practice.accsystem.service;

import com.practice.accsystem.dto.contractStage.ContractStageGetDto;
import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractStageService {
    boolean hasAccessToContractStage(AppUserEntity user, ContractStageEntity contractStage);

    ContractStageEntity createContractStage(ContractEntity contract, ContractStageEntity contractStage);

    ContractStageEntity findContractStageById(ContractEntity contract, Long contractStageId);

    Page<ContractStageEntity> findAllContractStageByContract(ContractEntity contract, Pageable pageable);

    ContractStageEntity updateContractStage(ContractStageEntity oldContractStage, ContractStageEntity newContractStage);

    ContractStageEntity deleteContractStage(ContractStageEntity contractStage);
}
