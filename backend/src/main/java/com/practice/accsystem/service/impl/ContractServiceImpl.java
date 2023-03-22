package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.ExcelRecord;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.repository.ContractRepository;
import com.practice.accsystem.service.ContractService;
import com.practice.accsystem.service.CounterpartyContractService;
import com.practice.accsystem.service.ExcelService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ContractServiceImpl implements ContractService {
    private static final String CONTRACTS_TEMPLATE_RESOURCE = "classpath:excel/templates/contracts.xlt";
    private static final String CONTRACT_STAGES_TEMPLATE_RESOURCE = "classpath:excel/templates/contractStages.xlt";

    private final ExcelService excelService;
    private final CounterpartyContractService counterpartyContractService;
    private final ContractRepository contractRepository;
    private final ResourceLoader resourceLoader;

    public ContractServiceImpl(ExcelService excelService, @Lazy CounterpartyContractService counterpartyContractService, ContractRepository contractRepository, ResourceLoader resourceLoader) {
        this.excelService = excelService;
        this.counterpartyContractService = counterpartyContractService;
        this.contractRepository = contractRepository;
        this.resourceLoader = resourceLoader;
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
                                                 Date startPeriod,
                                                 Date endPeriod,
                                                 Pageable pageable) {
        return contractRepository.findAllWithFilters(assignedUserId, title,
                contractType, minSum, maxSum, startPeriod, endPeriod, pageable);
    }

    @Override
    public List<ContractEntity> findAllContractsByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd) {
        return contractRepository.findAllByAssignedUserAndPlanStartDateAfterAndPlanEndDateBefore(user, periodStart, periodEnd);
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

    @Override
    public ByteArrayResource createContractsReportByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd) {
        List<ExcelRecord> excelRecords = new ArrayList<>();
        excelRecords.addAll(findAllContractsByUserInPeriodByPlanDeadline(user, periodStart, periodEnd));
        excelRecords.addAll(counterpartyContractService.findAllCounterpartyContractsByUserInPeriodByPlanDeadline(user, periodStart, periodEnd));

        Resource template = resourceLoader.getResource(CONTRACTS_TEMPLATE_RESOURCE);

        return new ByteArrayResource(excelService.writeEntitiesInTemplateLineByLine(excelRecords, template));
    }

    @Override
    public ByteArrayResource createContractStageReport(ContractEntity contract) {
        Resource template = resourceLoader.getResource(CONTRACT_STAGES_TEMPLATE_RESOURCE);

        return new ByteArrayResource(
                excelService.writeEntitiesInTemplateLineByLine(new ArrayList<>(contract.getContractStages()), template));
    }
}
