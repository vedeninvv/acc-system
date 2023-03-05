package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.exception.NotHasPermissionException;
import com.practice.accsystem.repository.ContractRepository;
import com.practice.accsystem.service.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public ContractEntity createContract(AppUserEntity user, ContractEntity contract) {
        contract.setAssignedUser(user);

        return contractRepository.save(contract);
    }

    @Override
    public ContractEntity findContractById(Long contractId) {
        return contractRepository.findById(contractId).orElseThrow(() ->
                new NotFoundEntityException(String.format("Contract with id '%d' not found", contractId))
        );
    }

    @Override
    public Page<ContractEntity> findAllContracts(Long assignedUserId,
                                                 String title,
                                                 ContractType contractType,
                                                 BigDecimal minSum,
                                                 BigDecimal maxSum,
                                                 Pageable pageable) {
        if (title != null) {
            title = title.toLowerCase(Locale.ROOT);
        }

        return contractRepository.findAllWithFilters(assignedUserId, title,
                contractType, minSum, maxSum, pageable);
    }

    @Override
    public ContractEntity updateContract(Long contractId, AppUserEntity user, ContractEntity newContract) {
        ContractEntity contract = contractRepository.findById(contractId).orElseThrow(() ->
                new NotFoundEntityException(String.format("Contract with id '%d' not found when try to update", contractId))
        );

        if (!hasAccessToContract(user, contract)) {
            throw new NotHasPermissionException(
                    String.format("Can not update contract with id '%d' by user with id '%d'", contractId, user.getId()));
        }

        contract.setTitle(newContract.getTitle());
        contract.setContractType(newContract.getContractType());
        contract.setSum(newContract.getSum());
        contract.setPlanStartDate(newContract.getPlanStartDate());
        contract.setPlanEndDate(newContract.getPlanEndDate());
        contract.setFactStartDate(newContract.getFactStartDate());
        contract.setFactEndDate(newContract.getFactEndDate());

        return contractRepository.save(contract);
    }

    @Override
    public ContractEntity deleteContract(AppUserEntity user, Long contractId) {
        ContractEntity contract = contractRepository.findById(contractId).orElseThrow(() ->
                new NotFoundEntityException(String.format("Contract with id '%d' not found when try to delete", contractId)));

        if (hasAccessToContract(user, contract)) {
            contractRepository.delete(contract);
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not update contract with id '%d' by user with id '%d'", contractId, user.getId()));
        }

        return contract;
    }

    @Override
    public boolean hasAccessToContract(AppUserEntity user, ContractEntity contract) {
        boolean hasAuthorityToWriteOnlyManagingContracts = user.getRole().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("contract:write:self"));
        boolean hasAuthorityToWriteAllContracts = user.getRole().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("contract:write:all"));
        boolean isAssignedUser = contract.getAssignedUser().equals(user);

        return hasAuthorityToWriteAllContracts || (isAssignedUser && hasAuthorityToWriteOnlyManagingContracts);
    }
}
