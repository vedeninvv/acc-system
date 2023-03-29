package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.CounterpartyEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CounterpartyContractRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CounterpartyRepository counterpartyRepository;

    @Autowired
    private CounterpartyContractRepository counterpartyContractRepository;

    private AppUserEntity user;

    private ContractEntity contract1;
    private ContractEntity contract2;

    private CounterpartyContractEntity counterpartyContract1;
    private CounterpartyContractEntity counterpartyContract2;
    private CounterpartyContractEntity counterpartyContract3;
    private CounterpartyContractEntity counterpartyContract4;


    @BeforeEach
    void prepareCounterpartyContracts() {
        user = AppUserEntity.builder().username("user").role(Role.USER).build();
        userRepository.save(user);

        contract1 = contractRepository.save(
                ContractEntity.builder().assignedUser(user).build());
        contract2 = contractRepository.save(
                ContractEntity.builder().assignedUser(user).build());

        CounterpartyEntity counterparty1 = new CounterpartyEntity();
        CounterpartyEntity counterparty2 = new CounterpartyEntity();
        counterpartyRepository.saveAll(Arrays.asList(counterparty1, counterparty2));

        counterpartyContract1 = CounterpartyContractEntity.builder()
                .contract(contract1)
                .counterparty(counterparty1)
                .contractType(ContractType.SUPPLY)
                .title("Тестовый контракт1 с контрагентом1")
                .sum(BigDecimal.valueOf(100.10))
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 10).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 20).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JULY, 7).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JULY, 12).getTime())
                .build();
        counterpartyContract2 = CounterpartyContractEntity.builder()
                .contract(contract1)
                .counterparty(counterparty2)
                .contractType(ContractType.PURCHASE)
                .title("Тестовый контракт2 с контрагентом2")
                .sum(BigDecimal.valueOf(200.20))
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 15).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 16).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JUNE, 15).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JUNE, 16).getTime())
                .build();
        counterpartyContract3 = CounterpartyContractEntity.builder()
                .contract(contract2)
                .counterparty(counterparty1)
                .contractType(ContractType.SUPPLY)
                .title("Тестовый контракт3 с контрагентом1")
                .sum(BigDecimal.valueOf(300.30))
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 10).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 20).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JULY, 7).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JULY, 12).getTime())
                .build();
        counterpartyContract4 = CounterpartyContractEntity.builder()
                .contract(contract2)
                .counterparty(counterparty2)
                .contractType(ContractType.PURCHASE)
                .title("Тестовый контракт4 с контрагентом2")
                .sum(BigDecimal.valueOf(400.40))
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .build();
        counterpartyContractRepository.saveAll(Arrays.asList(counterpartyContract1, counterpartyContract2,
                counterpartyContract3, counterpartyContract4));
    }

    @Test
    void testFindAllByContractWithoutFilters() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Arrays.asList(counterpartyContract1, counterpartyContract2);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract1, null, null, null, null, null, null,
                PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByContractWithFilterByCounterpartyId() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Collections.singletonList(counterpartyContract1);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract1, 1L, null, null, null, null, null,
                PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByContractWithFilterByFullTitle() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Collections.singletonList(counterpartyContract2);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract1, null, "Тестовый контракт2 с контрагентом2", null, null, null, null,
                PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByContractWithFilterByPartOfTitle() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Arrays.asList(counterpartyContract1, counterpartyContract2);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract1, null, "Тестовый", null, null, null, null,
                PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByContractWithFilterByMinSum() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Arrays.asList(counterpartyContract1, counterpartyContract2);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract1, null, null, BigDecimal.valueOf(100), null, null, null,
                PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByContractWithFilterByMaxSum() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Arrays.asList(counterpartyContract3, counterpartyContract4);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract2, null, null, null, BigDecimal.valueOf(500), null, null,
                PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByContractWithFilterByStartPeriod() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Collections.singletonList(counterpartyContract1);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract1, null, null, null, null, new GregorianCalendar(2023, Calendar.JULY, 1).getTime(), null,
                PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByContractWithFilterByEndPeriod() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Collections.singletonList(counterpartyContract4);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract2, null, null, null, null, null,
                new GregorianCalendar(2023, Calendar.JUNE, 1).getTime(), PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByContractWithAllFilters() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Collections.singletonList(counterpartyContract4);

        Page<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository.findAllByContract(
                contract2,
                2L,
                "Тестовый контракт4 с контрагентом2",
                BigDecimal.valueOf(400),
                BigDecimal.valueOf(500),
                new GregorianCalendar(2023, Calendar.JUNE, 1).getTime(),
                new GregorianCalendar(2023, Calendar.JUNE, 10).getTime(),
                PageRequest.of(0, 10)
        );

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }

    @Test
    void testFindAllByUserInPeriodByPlanDeadline() {
        List<CounterpartyContractEntity> expectedCounterpartyContracts = Arrays.asList(counterpartyContract2, counterpartyContract4);

        List<CounterpartyContractEntity> actualCounterpartyContracts = counterpartyContractRepository
                .findAllByUserInPeriodByPlanDeadline(
                        user,
                        new GregorianCalendar(2023, Calendar.JUNE, 1).getTime(),
                        new GregorianCalendar(2023, Calendar.JUNE, 17).getTime());

        assertThat(actualCounterpartyContracts).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCounterpartyContracts);
    }
}
