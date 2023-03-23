package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.repository.ContractRepository;
import com.practice.accsystem.service.impl.ContractServiceImpl;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContractServiceImplTest {
    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ContractServiceImpl contractService;

    @Test
    void testCreateContract() {
        AppUserEntity user = new AppUserEntity();
        Date currentDate = new Date();
        ContractEntity contract = ContractEntity.builder()
                .id(null)
                .assignedUser(null)
                .contractStages(new HashSet<>())
                .counterpartyContracts(new HashSet<>())
                .contractType(ContractType.SUPPLY)
                .title("Тестовый контракт")
                .sum(BigDecimal.valueOf(1000.20))
                .planStartDate(currentDate)
                .factStartDate(currentDate)
                .planEndDate(currentDate)
                .planEndDate(currentDate)
                .build();
        ContractEntity expectedContract = ContractEntity.builder()
                .id(null)
                .assignedUser(user)
                .contractStages(new HashSet<>())
                .counterpartyContracts(new HashSet<>())
                .contractType(ContractType.SUPPLY)
                .title("Тестовый контракт")
                .sum(BigDecimal.valueOf(1000.20))
                .planStartDate(currentDate)
                .factStartDate(currentDate)
                .planEndDate(currentDate)
                .planEndDate(currentDate)
                .build();
        when(contractRepository.save(contract)).thenReturn(expectedContract);
        ArgumentCaptor<ContractEntity> contractCaptor = ArgumentCaptor.forClass(ContractEntity.class);

        ContractEntity actualContract = contractService.createContract(user, contract);

        verify(contractRepository, times(1)).save(contractCaptor.capture());
        ContractEntity actualContractInArgumentToSave = contractCaptor.getValue();
        assertEqualsContractsByAllFields(actualContractInArgumentToSave, expectedContract);
        assertThat(actualContract).isEqualTo(expectedContract);
    }

    @Test
    void testFindContractByExistingId() {
        ContractEntity expectedContract = ContractEntity.builder().id(1L).build();
        when(contractRepository.findById(1L)).thenReturn(Optional.of(expectedContract));

        ContractEntity actualContract = contractService.findContractById(1L);

        assertThat(expectedContract).isEqualTo(actualContract);
    }

    @Test
    void testFindContractByNotExistingId() {
        when(contractRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contractService.findContractById(1L)).isInstanceOf(NotFoundEntityException.class);
    }

    @Test
    void testFindAllContractsWithoutFilters() {
        Page<ContractEntity> expectedContracts = new PageImpl<>(Arrays.asList(
                new ContractEntity(),
                new ContractEntity()
        ));
        when(contractRepository.findAllWithFilters(null, null, null, null, null,
                null, null, PageRequest.of(1, 2)))
                .thenReturn(expectedContracts);

        Page<ContractEntity> actualContracts = contractService.findAllContracts(null, null,
                null, null, null, null, null, PageRequest.of(1, 2));

        verify(contractRepository, times(1)).findAllWithFilters(null, null,
                null, null, null, null, null, PageRequest.of(1, 2));
        assertThat(actualContracts).isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllContractsWithAllFilters() {
        Date currentDate = new Date();
        Page<ContractEntity> expectedContracts = new PageImpl<>(Arrays.asList(
                new ContractEntity(),
                new ContractEntity()
        ));
        when(contractRepository.findAllWithFilters(1L, "Test title", ContractType.SUPPLY, BigDecimal.valueOf(100.01),
                BigDecimal.valueOf(200.01), currentDate, currentDate, PageRequest.of(1, 2)))
                .thenReturn(expectedContracts);

        Page<ContractEntity> actualContracts = contractService.findAllContracts(1L, "Test title", ContractType.SUPPLY, BigDecimal.valueOf(100.01),
                BigDecimal.valueOf(200.01), currentDate, currentDate, PageRequest.of(1, 2));

        verify(contractRepository, times(1)).findAllWithFilters(1L, "Test title", ContractType.SUPPLY, BigDecimal.valueOf(100.01),
                BigDecimal.valueOf(200.01), currentDate, currentDate, PageRequest.of(1, 2));
        assertThat(actualContracts).isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllContractsByUserInPeriodByPlanDeadline() {
        Date currentDate = new Date();
        AppUserEntity user = new AppUserEntity();
        List<ContractEntity> expectedContracts = new ArrayList<>();
        when(contractRepository.findAllByAssignedUserAndPlanStartDateAfterAndPlanEndDateBefore(user, currentDate, currentDate))
                .thenReturn(expectedContracts);

        List<ContractEntity> actualContracts = contractService.findAllContractsByUserInPeriodByPlanDeadline(user, currentDate, currentDate);

        verify(contractRepository, times(1))
                .findAllByAssignedUserAndPlanStartDateAfterAndPlanEndDateBefore(user, currentDate, currentDate);
        assertThat(actualContracts).isEqualTo(expectedContracts);
    }

    @Test
    void testUpdateContract() {
        AppUserEntity user = new AppUserEntity();
        Date currentDate = new Date();
        ContractEntity contract = ContractEntity.builder()
                .id(1L)
                .assignedUser(user)
                .contractStages(new HashSet<>())
                .counterpartyContracts(new HashSet<>())
                .contractType(ContractType.SUPPLY)
                .title("Тестовый контракт")
                .sum(BigDecimal.valueOf(1000.20))
                .planStartDate(currentDate)
                .factStartDate(currentDate)
                .planEndDate(currentDate)
                .planEndDate(currentDate)
                .build();
        Date updatedDate = new Date();
        ContractEntity updatingContent = ContractEntity.builder()
                .id(null)
                .assignedUser(null)
                .contractStages(null)
                .counterpartyContracts(null)
                .contractType(ContractType.PURCHASE)
                .title("Обновленный контракт")
                .sum(BigDecimal.valueOf(2000.40))
                .planStartDate(updatedDate)
                .factStartDate(updatedDate)
                .planEndDate(updatedDate)
                .planEndDate(updatedDate)
                .build();
        ContractEntity expectedContract = ContractEntity.builder()
                .id(1L)
                .assignedUser(user)
                .contractStages(new HashSet<>())
                .counterpartyContracts(new HashSet<>())
                .contractType(ContractType.PURCHASE)
                .title("Обновленный контракт")
                .sum(BigDecimal.valueOf(2000.40))
                .planStartDate(updatedDate)
                .factStartDate(updatedDate)
                .planEndDate(updatedDate)
                .planEndDate(updatedDate)
                .build();
        when(contractRepository.save(contract)).thenReturn(expectedContract);
        ArgumentCaptor<ContractEntity> contractCaptor = ArgumentCaptor.forClass(ContractEntity.class);

        ContractEntity actualUpdatedContract = contractService.updateContract(contract, updatingContent);

        verify(contractRepository, times(1)).save(contractCaptor.capture());
        ContractEntity actualArgumentToSaveContract = contractCaptor.getValue();
        assertThat(actualUpdatedContract).isEqualTo(expectedContract);
        assertEqualsContractsByAllFields(actualArgumentToSaveContract, expectedContract);
    }

    @Test
    public void testDeleteContract() {
        AppUserEntity user = new AppUserEntity();
        Date currentDate = new Date();
        ContractEntity contract = ContractEntity.builder()
                .id(1L)
                .assignedUser(user)
                .contractStages(new HashSet<>())
                .counterpartyContracts(new HashSet<>())
                .contractType(ContractType.SUPPLY)
                .title("Тестовый контракт")
                .sum(BigDecimal.valueOf(1000.20))
                .planStartDate(currentDate)
                .factStartDate(currentDate)
                .planEndDate(currentDate)
                .planEndDate(currentDate)
                .build();

        ContractEntity deletedContract = contractService.deleteContract(contract);

        verify(contractRepository, times(1)).delete(contract);
        assertThat(deletedContract).isEqualTo(contract);
    }

    @Test
    public void testHasAccessToContractWhenHasAccessBecauseAdmin() {
        AppUserEntity admin = AppUserEntity.builder().role(Role.ADMIN).build();
        AppUserEntity assignedUser = new AppUserEntity();
        ContractEntity contract = ContractEntity.builder().assignedUser(assignedUser).build();

        boolean actualAccess = contractService.hasAccessToContract(admin, contract);

        assertThat(actualAccess).isTrue();
    }

    @Test
    public void testHasAccessToContractWhenHasAccessBecauseAssigned() {
        AppUserEntity assignedUser = AppUserEntity.builder().role(Role.USER).build();
        ContractEntity contract = ContractEntity.builder().assignedUser(assignedUser).build();

        boolean actualAccess = contractService.hasAccessToContract(assignedUser, contract);

        assertThat(actualAccess).isTrue();
    }

    @Test
    public void testHasAccessToContractWhenNotHasAccessBecauseNotAdminAndNotAssigned() {
        AppUserEntity user = AppUserEntity.builder().role(Role.USER).build();
        AppUserEntity assignedUser = new AppUserEntity();
        ContractEntity contract = ContractEntity.builder().assignedUser(assignedUser).build();

        boolean actualAccess = contractService.hasAccessToContract(user, contract);

        assertThat(actualAccess).isFalse();
    }

    private void assertEqualsContractsByAllFields(ContractEntity actual, ContractEntity expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getAssignedUser()).isEqualTo(expected.getAssignedUser());
        assertThat(actual.getContractStages()).isEqualTo(expected.getContractStages());
        assertThat(actual.getCounterpartyContracts()).isEqualTo(expected.getCounterpartyContracts());
        assertThat(actual.getContractType()).isEqualTo(expected.getContractType());
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getSum()).isEqualTo(expected.getSum());
        assertThat(actual.getPlanStartDate()).isEqualTo(expected.getPlanStartDate());
        assertThat(actual.getPlanEndDate()).isEqualTo(expected.getPlanEndDate());
        assertThat(actual.getFactStartDate()).isEqualTo(expected.getFactStartDate());
        assertThat(actual.getFactEndDate()).isEqualTo(expected.getFactEndDate());
    }
}

