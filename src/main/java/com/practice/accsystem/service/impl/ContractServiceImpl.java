package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotFoundEntityException;
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
    public ContractEntity updateContract(ContractEntity oldContract, ContractEntity newContract) {
        oldContract.setTitle(newContract.getTitle());
        oldContract.setContractType(newContract.getContractType());
        oldContract.setSum(newContract.getSum());
        oldContract.setPlanStartDate(newContract.getPlanStartDate());
        oldContract.setPlanEndDate(newContract.getPlanEndDate());
        oldContract.setFactStartDate(newContract.getFactStartDate());
        oldContract.setFactEndDate(newContract.getFactEndDate());

        return contractRepository.save(oldContract);
    }

    @Override
    public ContractEntity deleteContract(ContractEntity contract) {
        contractRepository.delete(contract);

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
