package com.practice.accsystem.service;

import com.practice.accsystem.entity.*;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.repository.CounterpartyContractRepository;
import com.practice.accsystem.service.impl.CounterpartyContractServiceImpl;
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
public class CounterpartyContractServiceImplTest {
    @Mock
    private CounterpartyContractRepository counterpartyContractRepository;

    @Mock
    private ContractService contractService;

    @InjectMocks
    private CounterpartyContractServiceImpl counterpartyContractService;

    @Test
    void testCreateCounterpartyContract() {
        Date currentDate = new Date();
        ContractEntity contract = new ContractEntity();
        CounterpartyEntity counterparty = new CounterpartyEntity();
        CounterpartyContractEntity counterpartyContract = CounterpartyContractEntity.builder()
                .title("Тестовый контракт с контрагентом")
                .sum(BigDecimal.valueOf(10.20))
                .contractType(ContractType.SUPPLY)
                .planStartDate(currentDate)
                .planEndDate(currentDate)
                .factStartDate(currentDate)
                .factEndDate(currentDate)
                .build();
        CounterpartyContractEntity expectedCounterpartyContract = CounterpartyContractEntity.builder()
                .title("Тестовый контракт с контрагентом")
                .sum(BigDecimal.valueOf(10.20))
                .contractType(ContractType.SUPPLY)
                .planStartDate(currentDate)
                .planEndDate(currentDate)
                .factStartDate(currentDate)
                .factEndDate(currentDate)
                .contract(contract)
                .counterparty(counterparty)
                .build();
        when(counterpartyContractRepository.save(counterpartyContract)).thenReturn(expectedCounterpartyContract);
        ArgumentCaptor<CounterpartyContractEntity> counterpartyContractCaptor = ArgumentCaptor.forClass(CounterpartyContractEntity.class);

        CounterpartyContractEntity actualCounterpartyContract = counterpartyContractService.createCounterpartyContract(
                contract, counterparty, counterpartyContract);

        verify(counterpartyContractRepository, times(1)).save(counterpartyContractCaptor.capture());
        CounterpartyContractEntity actualCounterpartyContractInArgumentToSave = counterpartyContractCaptor.getValue();
        assertEqualsCounterpartyContractByAllFields(actualCounterpartyContractInArgumentToSave, expectedCounterpartyContract);
        assertThat(actualCounterpartyContract).isEqualTo(expectedCounterpartyContract);
    }

    @Test
    void testFindCounterpartyContractByExistingId() {
        ContractEntity contract = new ContractEntity();
        CounterpartyContractEntity counterpartyContract = new CounterpartyContractEntity();
        when(counterpartyContractRepository.findByContractAndId(contract, 1L))
                .thenReturn(Optional.of(counterpartyContract));

        CounterpartyContractEntity actualCounterpartyContract = counterpartyContractService.findCounterpartyContractById(contract, 1L);

        assertThat(actualCounterpartyContract).isEqualTo(counterpartyContract);
    }

    @Test
    void testFindCounterpartyContractByNotExistingId() {
        ContractEntity contract = new ContractEntity();
        when(counterpartyContractRepository.findByContractAndId(contract, 1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> counterpartyContractService.findCounterpartyContractById(contract, 1L))
                .isInstanceOf(NotFoundEntityException.class);
    }

    @Test
    void testFindAllCounterpartyContractsByContractWithoutFilters() {
        Page<CounterpartyContractEntity> expectedCounterpartyContracts = new PageImpl<>(Arrays.asList(
                new CounterpartyContractEntity(),
                new CounterpartyContractEntity()
        ));
        ContractEntity contract = new ContractEntity();
        when(counterpartyContractRepository.findAllByContract(contract, null, null, null, null,
                null, null, PageRequest.of(1, 2)))
                .thenReturn(expectedCounterpartyContracts);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractService.findAllCounterpartyContractsByContract(
                contract, null, null, null, null, null, null, PageRequest.of(1, 2));

        verify(counterpartyContractRepository, times(1)).findAllByContract(contract, null,
                null, null, null, null, null, PageRequest.of(1, 2));
        assertThat(actualCounterpartyContracts).isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllCounterpartyContractsByContractWithAllFilters() {
        Page<CounterpartyContractEntity> expectedCounterpartyContracts = new PageImpl<>(Arrays.asList(
                new CounterpartyContractEntity(),
                new CounterpartyContractEntity()
        ));
        ContractEntity contract = new ContractEntity();
        Date currentDate = new Date();
        when(counterpartyContractRepository.findAllByContract(contract, 1L, "1", BigDecimal.valueOf(1),
                BigDecimal.valueOf(2), currentDate, currentDate, PageRequest.of(1, 2)))
                .thenReturn(expectedCounterpartyContracts);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractService.findAllCounterpartyContractsByContract(
                contract, 1L, "1", BigDecimal.valueOf(1), BigDecimal.valueOf(2), currentDate,
                currentDate, PageRequest.of(1, 2));

        verify(counterpartyContractRepository, times(1)).findAllByContract(contract, 1L,
                "1", BigDecimal.valueOf(1), BigDecimal.valueOf(2), currentDate, currentDate, PageRequest.of(1, 2));
        assertThat(actualCounterpartyContracts).isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testUpdateCounterpartyContract() {
        Date currentDate = new Date();
        ContractEntity contract = new ContractEntity();
        CounterpartyEntity counterparty = new CounterpartyEntity();
        CounterpartyContractEntity counterpartyContract = CounterpartyContractEntity.builder()
                .id(1L)
                .title("Тестовый контракт с контрагентом")
                .sum(BigDecimal.valueOf(10.20))
                .contractType(ContractType.SUPPLY)
                .planStartDate(currentDate)
                .planEndDate(currentDate)
                .factStartDate(currentDate)
                .factEndDate(currentDate)
                .contract(contract)
                .counterparty(counterparty)
                .build();
        Date newCurrentDate = new Date();
        CounterpartyContractEntity updatingContent = CounterpartyContractEntity.builder()
                .title("Обновленный тестовый контракт с контрагентом")
                .sum(BigDecimal.valueOf(20.20))
                .contractType(ContractType.PURCHASE)
                .planStartDate(newCurrentDate)
                .planEndDate(newCurrentDate)
                .factStartDate(newCurrentDate)
                .factEndDate(newCurrentDate)
                .counterparty(counterparty)
                .build();
        CounterpartyContractEntity expectedCounterpartyContract = CounterpartyContractEntity.builder()
                .id(1L)
                .title("Обновленный тестовый контракт с контрагентом")
                .sum(BigDecimal.valueOf(20.20))
                .contractType(ContractType.PURCHASE)
                .planStartDate(newCurrentDate)
                .planEndDate(newCurrentDate)
                .factStartDate(newCurrentDate)
                .factEndDate(newCurrentDate)
                .contract(contract)
                .counterparty(counterparty)
                .build();
        when(counterpartyContractRepository.save(counterpartyContract)).thenReturn(expectedCounterpartyContract);
        ArgumentCaptor<CounterpartyContractEntity> counterpartyContractCaptor = ArgumentCaptor.forClass(CounterpartyContractEntity.class);

        CounterpartyContractEntity actualCounterpartyContract = counterpartyContractService.updateCounterpartyContract(
                counterpartyContract, updatingContent);

        verify(counterpartyContractRepository, times(1)).save(counterpartyContractCaptor.capture());
        CounterpartyContractEntity actualCounterpartyContractInArgToSave = counterpartyContractCaptor.getValue();
        assertThat(actualCounterpartyContract).isEqualTo(expectedCounterpartyContract);
        assertEqualsCounterpartyContractByAllFields(actualCounterpartyContractInArgToSave, expectedCounterpartyContract);
    }

    @Test
    void testDeleteCounterpartyContract() {
        Date currentDate = new Date();
        ContractEntity contract = new ContractEntity();
        CounterpartyEntity counterparty = new CounterpartyEntity();
        CounterpartyContractEntity counterpartyContract = CounterpartyContractEntity.builder()
                .id(1L)
                .title("Обновленный тестовый контракт с контрагентом")
                .sum(BigDecimal.valueOf(20.20))
                .contractType(ContractType.PURCHASE)
                .planStartDate(currentDate)
                .planEndDate(currentDate)
                .factStartDate(currentDate)
                .factEndDate(currentDate)
                .contract(contract)
                .counterparty(counterparty)
                .build();

        CounterpartyContractEntity deletedCounterpartyContract = counterpartyContractService.deleteCounterpartyContract(counterpartyContract);

        verify(counterpartyContractRepository, times(1)).delete(counterpartyContract);
        assertThat(deletedCounterpartyContract).isEqualTo(counterpartyContract);
    }

    @Test
    void testHasAccessToCounterpartyContractWhenHasAccessToContract() {
        AppUserEntity user = new AppUserEntity();
        CounterpartyContractEntity counterpartyContract = new CounterpartyContractEntity();
        ContractEntity contract = ContractEntity.builder()
                .counterpartyContracts(new HashSet<>(Collections.singletonList(counterpartyContract)))
                .build();
        counterpartyContract.setContract(contract);
        when(contractService.hasAccessToContract(user, contract)).thenReturn(true);

        boolean actual = counterpartyContractService.hasAccessToCounterpartyContract(user, counterpartyContract);

        assertThat(actual).isTrue();
    }

    @Test
    void testHasAccessToCounterpartyContractWhenNotHasAccessToContract() {
        AppUserEntity user = new AppUserEntity();
        CounterpartyContractEntity counterpartyContract = new CounterpartyContractEntity();
        ContractEntity contract = ContractEntity.builder()
                .counterpartyContracts(new HashSet<>(Collections.singletonList(counterpartyContract)))
                .build();
        counterpartyContract.setContract(contract);
        when(contractService.hasAccessToContract(user, contract)).thenReturn(false);

        boolean actual = counterpartyContractService.hasAccessToCounterpartyContract(user, counterpartyContract);

        assertThat(actual).isFalse();
    }

    private void assertEqualsCounterpartyContractByAllFields(CounterpartyContractEntity actual, CounterpartyContractEntity expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getContract()).isEqualTo(expected.getContract());
        assertThat(actual.getCounterparty()).isEqualTo(expected.getCounterparty());
        assertThat(actual.getContractType()).isEqualTo(expected.getContractType());
        assertThat(actual.getPlanStartDate()).isEqualTo(expected.getPlanStartDate());
        assertThat(actual.getPlanEndDate()).isEqualTo(expected.getPlanEndDate());
        assertThat(actual.getFactStartDate()).isEqualTo(expected.getFactStartDate());
        assertThat(actual.getFactEndDate()).isEqualTo(expected.getFactEndDate());
        assertThat(actual.getSum()).isEqualTo(expected.getSum());
    }
}
