package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import com.practice.accsystem.entity.ExpenseEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.repository.ContractStageRepository;
import com.practice.accsystem.service.impl.ContractStageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContractStageServiceImplTest {
    @Mock
    private ContractService contractService;

    @Mock
    private ContractStageRepository contractStageRepository;

    @InjectMocks
    private ContractStageServiceImpl contractStageService;

    @Test
    void testHasAccessToContractStageWhenHasAccessToContract() {
        AppUserEntity user = new AppUserEntity();
        ContractStageEntity contractStage = new ContractStageEntity();
        ContractEntity contract = ContractEntity.builder()
                .contractStages(new HashSet<>(Collections.singletonList(contractStage)))
                .build();
        contractStage.setContract(contract);
        when(contractService.hasAccessToContract(user, contract)).thenReturn(true);

        boolean actual = contractStageService.hasAccessToContractStage(user, contractStage);

        assertThat(actual).isTrue();
    }

    @Test
    void testHasAccessToContractStageWhenNotHasAccessToContract() {
        AppUserEntity user = new AppUserEntity();
        ContractStageEntity contractStage = new ContractStageEntity();
        ContractEntity contract = ContractEntity.builder()
                .contractStages(new HashSet<>(Collections.singletonList(contractStage)))
                .build();
        contractStage.setContract(contract);
        when(contractService.hasAccessToContract(user, contract)).thenReturn(false);

        boolean actual = contractStageService.hasAccessToContractStage(user, contractStage);

        assertThat(actual).isFalse();
    }

    @Test
    void testCreateContractStage() {
        Date current = new Date();
        ContractEntity contract = new ContractEntity();
        ContractStageEntity contractStage = ContractStageEntity.builder()
                .title("Тестовый этап")
                .sum(BigDecimal.valueOf(100.20))
                .planStartDate(current)
                .planEndDate(current)
                .factStartDate(current)
                .factEndDate(current)
                .expenses(new HashSet<>(Arrays.asList(
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)),
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(3.1), BigDecimal.valueOf(5.2)),
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(0.1), BigDecimal.valueOf(10.2))
                )))
                .build();
        ContractStageEntity expectedContractStage = ContractStageEntity.builder()
                .title("Тестовый этап")
                .sum(BigDecimal.valueOf(100.20))
                .planStartDate(current)
                .planEndDate(current)
                .factStartDate(current)
                .factEndDate(current)
                .planTotalExpenses(BigDecimal.valueOf(4.3))
                .factTotalExpenses(BigDecimal.valueOf(17.6))
                .contract(contract)
                .expenses(new HashSet<>(Arrays.asList(
                        new ExpenseEntity(null, contractStage, "1", BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)),
                        new ExpenseEntity(null, contractStage, "1", BigDecimal.valueOf(3.1), BigDecimal.valueOf(5.2)),
                        new ExpenseEntity(null, contractStage, "1", BigDecimal.valueOf(0.1), BigDecimal.valueOf(10.2))
                )))
                .build();
        when(contractStageRepository.save(contractStage)).thenReturn(expectedContractStage);
        ArgumentCaptor<ContractStageEntity> contractStageCaptor = ArgumentCaptor.forClass(ContractStageEntity.class);

        ContractStageEntity actualContractStage = contractStageService.createContractStage(contract, contractStage);

        verify(contractStageRepository, times(1)).save(contractStageCaptor.capture());
        ContractStageEntity actualContractStageInArgumentToSave = contractStageCaptor.getValue();
        assertThat(actualContractStageInArgumentToSave).usingRecursiveComparison().isEqualTo(expectedContractStage);
        assertThat(actualContractStage).isEqualTo(expectedContractStage);
    }

    @Test
    void testFindContractStageByExistingId() {
        ContractEntity contract = new ContractEntity();
        ContractStageEntity contractStage = new ContractStageEntity();
        when(contractStageRepository.findByContractAndId(contract, 1L)).thenReturn(Optional.of(contractStage));

        ContractStageEntity actualContractStage = contractStageService.findContractStageById(contract, 1L);

        assertThat(actualContractStage).isEqualTo(contractStage);
    }

    @Test
    void testFindContractStageByNotExistingId() {
        ContractEntity contract = new ContractEntity();
        when(contractStageRepository.findByContractAndId(contract, 1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contractStageService.findContractStageById(contract, 1L))
                .isInstanceOf(NotFoundEntityException.class);
    }

    @Test
    void testFindAllContractStagesWithoutFilters() {
        Page<ContractStageEntity> expectedContractStages = new PageImpl<>(Arrays.asList(
                new ContractStageEntity(),
                new ContractStageEntity()
        ));
        ContractEntity contract = new ContractEntity();
        when(contractStageRepository.findAllByContract(contract, null, null, null, null,
                null, PageRequest.of(1, 2)))
                .thenReturn(expectedContractStages);

        Page<ContractStageEntity> actualContractStages = contractStageService.findAllContractStageByContract(contract, null,
                null, null, null, null, PageRequest.of(1, 2));

        verify(contractStageRepository, times(1)).findAllByContract(contract, null,
                null, null, null, null, PageRequest.of(1, 2));
        assertThat(actualContractStages).isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllContractStagesWithAllFilters() {
        Date currentDate = new Date();
        Page<ContractStageEntity> expectedContractStages = new PageImpl<>(Arrays.asList(
                new ContractStageEntity(),
                new ContractStageEntity()
        ));
        ContractEntity contract = new ContractEntity();
        when(contractStageRepository.findAllByContract(contract, "Тестовый этап", BigDecimal.valueOf(100.01),
                BigDecimal.valueOf(10.10), currentDate, currentDate, PageRequest.of(1, 2)))
                .thenReturn(expectedContractStages);

        Page<ContractStageEntity> actualContractStages = contractStageService.findAllContractStageByContract(contract,
                "Тестовый этап", BigDecimal.valueOf(100.01), BigDecimal.valueOf(10.10), currentDate, currentDate, PageRequest.of(1, 2));

        verify(contractStageRepository, times(1)).findAllByContract(contract, "Тестовый этап",
                BigDecimal.valueOf(100.01), BigDecimal.valueOf(10.10), currentDate, currentDate, PageRequest.of(1, 2));
        assertThat(actualContractStages).isEqualTo(expectedContractStages);
    }

    @Test
    void testUpdateContractStage() {
        Date current = new Date();
        ContractEntity contract = new ContractEntity();
        ContractStageEntity contractStage = ContractStageEntity.builder()
                .id(1L)
                .contract(contract)
                .title("Тестовый этап")
                .sum(BigDecimal.valueOf(100.20))
                .planStartDate(current)
                .planEndDate(current)
                .factStartDate(current)
                .factEndDate(current)
                .planTotalExpenses(BigDecimal.valueOf(4.3))
                .factTotalExpenses(BigDecimal.valueOf(17.6))
                .expenses(new HashSet<>(Arrays.asList(
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)),
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(3.1), BigDecimal.valueOf(5.2)),
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(0.1), BigDecimal.valueOf(10.2))
                )))
                .build();
        Date newCurrent = new Date();
        HashSet<ExpenseEntity> updatingExpensesContent = new HashSet<>(Arrays.asList(
                new ExpenseEntity(null, null, "2", BigDecimal.valueOf(5.5), BigDecimal.valueOf(12.2)),
                new ExpenseEntity(null, null, "2", BigDecimal.valueOf(6.6), BigDecimal.valueOf(0.2))
        ));
        ContractStageEntity updatingContent = ContractStageEntity.builder()
                .title("Обновленный этап")
                .sum(BigDecimal.valueOf(200.10))
                .planStartDate(newCurrent)
                .planEndDate(newCurrent)
                .factStartDate(newCurrent)
                .factEndDate(newCurrent)
                .expenses(updatingExpensesContent)
                .build();
        ContractStageEntity expectedContractStage = ContractStageEntity.builder()
                .id(1L)
                .contract(contract)
                .title("Обновленный этап")
                .sum(BigDecimal.valueOf(200.10))
                .planStartDate(newCurrent)
                .planEndDate(newCurrent)
                .factStartDate(newCurrent)
                .factEndDate(newCurrent)
                .planTotalExpenses(BigDecimal.valueOf(12.1))
                .factTotalExpenses(BigDecimal.valueOf(12.4))
                .expenses(updatingExpensesContent)
                .build();
        when(contractStageRepository.save(contractStage)).thenReturn(expectedContractStage);
        ArgumentCaptor<ContractStageEntity> contractStageCaptor = ArgumentCaptor.forClass(ContractStageEntity.class);

        ContractStageEntity actualUpdatedContractStage = contractStageService.updateContractStage(contractStage, updatingContent);

        verify(contractStageRepository, times(1)).save(contractStageCaptor.capture());
        ContractStageEntity actualArgumentToSaveContractStage = contractStageCaptor.getValue();
        assertThat(actualUpdatedContractStage).isEqualTo(expectedContractStage);
        assertThat(actualArgumentToSaveContractStage).usingRecursiveComparison().isEqualTo(expectedContractStage);
    }

    @Test
    void testDeleteContractStage() {
        Date current = new Date();
        ContractEntity contract = new ContractEntity();
        ContractStageEntity contractStage = ContractStageEntity.builder()
                .id(1L)
                .contract(contract)
                .title("Тестовый этап")
                .sum(BigDecimal.valueOf(100.20))
                .planStartDate(current)
                .planEndDate(current)
                .factStartDate(current)
                .factEndDate(current)
                .planTotalExpenses(BigDecimal.valueOf(4.3))
                .factTotalExpenses(BigDecimal.valueOf(17.6))
                .expenses(new HashSet<>(Arrays.asList(
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)),
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(3.1), BigDecimal.valueOf(5.2)),
                        new ExpenseEntity(null, null, "1", BigDecimal.valueOf(0.1), BigDecimal.valueOf(10.2))
                )))
                .build();

        ContractStageEntity deletedContractStage = contractStageService.deleteContractStage(contractStage);

        verify(contractStageRepository, times(1)).delete(contractStage);
        assertThat(deletedContractStage).isEqualTo(contractStage);
    }
}
