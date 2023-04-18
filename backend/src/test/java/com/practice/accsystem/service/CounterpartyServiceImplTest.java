package com.practice.accsystem.service;

import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.CounterpartyEntity;
import com.practice.accsystem.exception.DuplicateUniqueValueException;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.exception.RelatedEntitiesCanNotBeDeleted;
import com.practice.accsystem.repository.CounterpartyRepository;
import com.practice.accsystem.service.impl.CounterpartyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CounterpartyServiceImplTest {
    @Mock
    private CounterpartyRepository counterpartyRepository;

    @InjectMocks
    private CounterpartyServiceImpl counterpartyService;

    @Test
    void testCreateCounterpartyWhenTitleAndInnNotExist() {
        CounterpartyEntity counterparty = CounterpartyEntity.builder()
                .title("Контрагент")
                .address("Адрес")
                .INN("123")
                .build();
        CounterpartyEntity expectedCounterparty = CounterpartyEntity.builder()
                .id(1L)
                .title("Контрагент")
                .address("Адрес")
                .INN("123")
                .build();
        when(counterpartyRepository.existsByTitle("Контрагент")).thenReturn(false);
        when(counterpartyRepository.existsByINN("123")).thenReturn(false);
        when(counterpartyRepository.save(counterparty)).thenReturn(expectedCounterparty);

        CounterpartyEntity actualCounterparty = counterpartyService.createCounterparty(counterparty);

        verify(counterpartyRepository, times(1)).save(counterparty);
        assertThat(actualCounterparty).isEqualTo(expectedCounterparty);
    }

    @Test
    void testCreateCounterpartyWhenTitleExist() {
        CounterpartyEntity counterparty = CounterpartyEntity.builder()
                .title("Контрагент")
                .address("Адрес")
                .INN("123")
                .build();
        when(counterpartyRepository.existsByTitle("Контрагент")).thenReturn(true);

        assertThatThrownBy(() -> counterpartyService.createCounterparty(counterparty)).isInstanceOf(DuplicateUniqueValueException.class);

        verify(counterpartyRepository, times(0)).save(counterparty);
    }

    @Test
    void testCreateCounterpartyWhenInnExist() {
        CounterpartyEntity counterparty = CounterpartyEntity.builder()
                .title("Контрагент")
                .address("Адрес")
                .INN("123")
                .build();
        when(counterpartyRepository.existsByTitle("Контрагент")).thenReturn(false);
        when(counterpartyRepository.existsByINN("123")).thenReturn(true);

        assertThatThrownBy(() -> counterpartyService.createCounterparty(counterparty)).isInstanceOf(DuplicateUniqueValueException.class);

        verify(counterpartyRepository, times(0)).save(counterparty);
    }

    @Test
    void testFindCounterpartyByExistingId() {
        CounterpartyEntity counterparty = CounterpartyEntity.builder().id(1L).build();
        when(counterpartyRepository.findById(1L)).thenReturn(Optional.of(counterparty));

        CounterpartyEntity actualCounterparty = counterpartyService.findCounterpartyById(1L);
        assertThat(actualCounterparty).isEqualTo(counterparty);
    }

    @Test
    void testFindCounterpartyByNotExistingId() {
        when(counterpartyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> counterpartyService.findCounterpartyById(1L))
                .isInstanceOf(NotFoundEntityException.class);
    }

    @Test
    void testFindAllCounterpartiesWithoutFilters() {
        Page<CounterpartyEntity> counterpartyEntities = new PageImpl<>(Arrays.asList(
                new CounterpartyEntity(),
                new CounterpartyEntity()
        ));
        when(counterpartyRepository.findAllWithFilters(null, null, null, PageRequest.of(1, 2)))
                .thenReturn(counterpartyEntities);

        Page<CounterpartyEntity> actualCounterpartyEntities = counterpartyService.findAllCounterparties(
                null, null, null, PageRequest.of(1, 2));
        assertThat(actualCounterpartyEntities).isEqualTo(counterpartyEntities);
    }

    @Test
    void testUpdateCounterpartyWhenTitleOrInnNotExist() {
        CounterpartyEntity counterparty = CounterpartyEntity.builder()
                .id(1L)
                .title("Контрагент")
                .address("Адрес")
                .INN("123")
                .build();
        CounterpartyEntity updatingContent = CounterpartyEntity.builder()
                .title("Обновленный контрагент")
                .address("Обновленный Адрес")
                .INN("456")
                .build();
        CounterpartyEntity expectedCounterparty = CounterpartyEntity.builder()
                .id(1L)
                .title("Обновленный контрагент")
                .address("Обновленный Адрес")
                .INN("456")
                .build();
        when(counterpartyRepository.existsByTitle("Обновленный контрагент")).thenReturn(false);
        when(counterpartyRepository.existsByINN("456")).thenReturn(false);
        when(counterpartyRepository.save(counterparty)).thenReturn(expectedCounterparty);
        ArgumentCaptor<CounterpartyEntity> counterpartyCaptor = ArgumentCaptor.forClass(CounterpartyEntity.class);

        CounterpartyEntity actualCounterparty = counterpartyService.updateCounterparty(counterparty, updatingContent);

        verify(counterpartyRepository, times(1)).save(counterpartyCaptor.capture());
        CounterpartyEntity actualCounterpartyInArgsToSave = counterpartyCaptor.getValue();
        assertThat(actualCounterparty).isEqualTo(expectedCounterparty);
        assertThat(actualCounterpartyInArgsToSave).usingRecursiveComparison().isEqualTo(expectedCounterparty);
    }

    @Test
    void testDeleteCounterpartyWhenHasRelatedEntities() {
        CounterpartyEntity counterparty = CounterpartyEntity.builder()
                .id(1L)
                .title("Контрагент")
                .address("Адрес")
                .INN("123")
                .counterpartyContracts(new HashSet<>(Arrays.asList(
                        new CounterpartyContractEntity(),
                        new CounterpartyContractEntity()
                )))
                .build();

        assertThatThrownBy(() -> counterpartyService.deleteCounterparty(counterparty))
                .isInstanceOf(RelatedEntitiesCanNotBeDeleted.class);
    }

    @Test
    void testDeleteCounterpartyWhenNotHasRelatedEntities() {
        CounterpartyEntity counterparty = CounterpartyEntity.builder()
                .id(1L)
                .title("Контрагент")
                .address("Адрес")
                .INN("123")
                .build();

        CounterpartyEntity deletedCounterparty = counterpartyService.deleteCounterparty(counterparty);

        assertThat(deletedCounterparty).isEqualTo(counterparty);
    }
}
