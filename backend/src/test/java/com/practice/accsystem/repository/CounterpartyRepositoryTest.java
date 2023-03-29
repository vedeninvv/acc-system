package com.practice.accsystem.repository;

import com.practice.accsystem.entity.CounterpartyEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CounterpartyRepositoryTest {
    @Autowired
    private CounterpartyRepository counterpartyRepository;

    private CounterpartyEntity counterparty1;
    private CounterpartyEntity counterparty2;
    private CounterpartyEntity counterparty3;
    private CounterpartyEntity counterparty4;

    @BeforeEach
    void prepareCounterparties() {
        counterparty1 = CounterpartyEntity.builder()
                .title("Контрагент 1")
                .address("Адрес контрагента 1")
                .INN("123456789")
                .build();
        counterparty2 = CounterpartyEntity.builder()
                .title("Контрагент 2")
                .address("Адрес контрагента 2")
                .INN("987654321")
                .build();
        counterparty3 = CounterpartyEntity.builder()
                .title("Контрагент 3")
                .address("Адрес контрагента 3")
                .INN("111111111")
                .build();
        counterparty4 = CounterpartyEntity.builder()
                .title("Контрагент 4")
                .address("Адрес контрагента 4")
                .INN("123498765")
                .build();

        counterpartyRepository.saveAll(Arrays.asList(counterparty1, counterparty2, counterparty3, counterparty4));
    }

    @Test
    void testFindAllWithFiltersWhenFiltersNull() {
        List<CounterpartyEntity> expectedCounterparties = Arrays.asList(counterparty1, counterparty2, counterparty3, counterparty4);

        Page<CounterpartyEntity> actualCounterparties = counterpartyRepository.findAllWithFilters(
                null, null, null, PageRequest.of(0, 10));

        assertThat(actualCounterparties).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedCounterparties);
    }

    @Test
    void testFindAllWithFiltersByTitle() {
        List<CounterpartyEntity> expectedCounterparties = Arrays.asList(counterparty1, counterparty2, counterparty3, counterparty4);

        Page<CounterpartyEntity> actualCounterparties = counterpartyRepository.findAllWithFilters(
                "агент", null, null, PageRequest.of(0, 10));

        assertThat(actualCounterparties).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedCounterparties);
    }

    @Test
    void testFindAllWithFiltersByAddress() {
        List<CounterpartyEntity> expectedCounterparties = Collections.singletonList(counterparty1);

        Page<CounterpartyEntity> actualCounterparties = counterpartyRepository.findAllWithFilters(
                null, "Адрес контрагента 1", null, PageRequest.of(0, 10));

        assertThat(actualCounterparties).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedCounterparties);
    }

    @Test
    void testFindAllWithFiltersByInn() {
        List<CounterpartyEntity> expectedCounterparties = Arrays.asList(counterparty1, counterparty4);

        Page<CounterpartyEntity> actualCounterparties = counterpartyRepository.findAllWithFilters(
                null, null, "1234", PageRequest.of(0, 10));

        assertThat(actualCounterparties).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedCounterparties);
    }

    @Test
    void testFindAllWithFiltersAll() {
        List<CounterpartyEntity> expectedCounterparties = Collections.singletonList(counterparty1);

        Page<CounterpartyEntity> actualCounterparties = counterpartyRepository.findAllWithFilters(
                "Контрагент 1", "Адрес контрагента 1", "1234", PageRequest.of(0, 10));

        assertThat(actualCounterparties).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedCounterparties);
    }
}
