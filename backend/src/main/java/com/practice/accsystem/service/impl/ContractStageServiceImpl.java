package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import com.practice.accsystem.entity.ExpenseEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.repository.ContractStageRepository;
import com.practice.accsystem.service.ContractService;
import com.practice.accsystem.service.ContractStageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class ContractStageServiceImpl implements ContractStageService {
    private final ContractService contractService;
    private final ContractStageRepository contractStageRepository;

    public ContractStageServiceImpl(ContractService contractService, ContractStageRepository contractStageRepository) {
        this.contractService = contractService;
        this.contractStageRepository = contractStageRepository;
    }

    @Override
    public boolean hasAccessToContractStage(AppUserEntity user, ContractStageEntity contractStage) {
        return contractService.hasAccessToContract(user, contractStage.getContract());
    }

    @Override
    public ContractStageEntity createContractStage(ContractEntity contract, ContractStageEntity contractStage) {
        contractStage.setPlanTotalExpenses(
                calculatePlanTotalExpense(contractStage)
        );

        contractStage.setFactTotalExpenses(
                calculateFactTotalExpense(contractStage)
        );

        contractStage.getExpenses().forEach(expense -> expense.setContractStage(contractStage));
        contractStage.setContract(contract);

        return contractStageRepository.save(contractStage);
    }

    @Override
    public ContractStageEntity findContractStageById(ContractEntity contract, Long contractStageId) {
        return contractStageRepository.findByContractAndId(contract, contractStageId).orElseThrow(() ->
                new NotFoundEntityException(
                        String.format("Not found contractStage with id '%d' for contract with id '%d'", contractStageId, contract.getId())));
    }

    @Override
    public Page<ContractStageEntity> findAllContractStageByContract(ContractEntity contract, String title, BigDecimal minSum,
                                                                    BigDecimal maxSum, Date startPeriod, Date endPeriod, Pageable pageable) {
        return contractStageRepository.findAllByContract(contract, title, minSum, maxSum, startPeriod, endPeriod, pageable);
    }

    @Override
    public ContractStageEntity updateContractStage(ContractStageEntity oldContractStage, ContractStageEntity newContractStage) {
        oldContractStage.setTitle(newContractStage.getTitle());
        oldContractStage.setSum(newContractStage.getSum());
        oldContractStage.setPlanStartDate(newContractStage.getPlanStartDate());
        oldContractStage.setPlanEndDate(newContractStage.getPlanEndDate());
        oldContractStage.setFactStartDate(newContractStage.getFactStartDate());
        oldContractStage.setFactEndDate(newContractStage.getFactEndDate());

        oldContractStage.getExpenses().clear();
        oldContractStage.getExpenses().addAll(newContractStage.getExpenses());
        oldContractStage.getExpenses().forEach(expense -> expense.setContractStage(oldContractStage));

        oldContractStage.setPlanTotalExpenses(
                calculatePlanTotalExpense(newContractStage)
        );
        oldContractStage.setFactTotalExpenses(
                calculateFactTotalExpense(newContractStage)
        );

        return contractStageRepository.save(oldContractStage);
    }

    @Override
    public ContractStageEntity deleteContractStage(ContractStageEntity contractStage) {
        contractStageRepository.delete(contractStage);

        return contractStage;
    }

    private BigDecimal calculatePlanTotalExpense(ContractStageEntity contractStage) {
        return contractStage.getExpenses().stream()
                .map(ExpenseEntity::getPlanAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateFactTotalExpense(ContractStageEntity contractStage) {
        return contractStage.getExpenses().stream()
                .map(ExpenseEntity::getFactAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
