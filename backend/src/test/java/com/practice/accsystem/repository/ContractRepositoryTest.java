package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class ContractRepositoryTest {
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private UserRepository userRepository;

    private ContractEntity contract1;
    private ContractEntity contract2;
    private ContractEntity contract3;
    private ContractEntity contract4;

    @BeforeEach
    void prepareContracts() {
        AppUserEntity user1 = AppUserEntity.builder().username("user1").role(Role.USER).build();
        AppUserEntity user2 = AppUserEntity.builder().username("user2").role(Role.USER).build();
        AppUserEntity admin = AppUserEntity.builder().username("admin").role(Role.ADMIN).build();
        userRepository.saveAll(Arrays.asList(user1, user2, admin));

        contract1 = ContractEntity.builder()
                .assignedUser(user1)
                .title("Тестовый контракт1")
                .sum(BigDecimal.valueOf(100.10))
                .contractType(ContractType.SUPPLY)
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 10).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 20).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JULY, 7).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JULY, 12).getTime())
                .build();
        contract2 = ContractEntity.builder()
                .assignedUser(user1)
                .title("Тестовый контракт2")
                .sum(BigDecimal.valueOf(200.20))
                .contractType(ContractType.PURCHASE)
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 15).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 16).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JUNE, 15).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JUNE, 16).getTime())
                .build();
        contract3 = ContractEntity.builder()
                .assignedUser(user2)
                .title("Тестовый контракт3 пользователя 2")
                .sum(BigDecimal.valueOf(300.30))
                .contractType(ContractType.WORKS)
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 10).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 20).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JULY, 7).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JULY, 12).getTime())
                .build();
        contract4 = ContractEntity.builder()
                .assignedUser(admin)
                .title("Тестовый контракт4 пользователя 3 (админа)")
                .sum(BigDecimal.valueOf(400.40))
                .contractType(ContractType.SUPPLY)
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .build();
        contractRepository.saveAll(Arrays.asList(contract1, contract2, contract3, contract4));
    }

    @Test
    void testFindAllWithoutFilters() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract1, contract2, contract3, contract4);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByUserIdWhenUserExist() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract1, contract2);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(1L, null,
                null, null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByUserIdWhenUserNotExist() {
        List<ContractEntity> expectedContracts = Collections.emptyList();

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(100L, null,
                null, null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByFullTitle() {
        List<ContractEntity> expectedContracts = Collections.singletonList(contract1);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, "Тестовый контракт1",
                null, null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByPartOfTitle() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract3, contract4);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, "пользова",
                null, null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByContractType() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract1, contract4);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                ContractType.SUPPLY, null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByMinSum() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract3, contract4);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, BigDecimal.valueOf(300), null, null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByMinSumAtEdge() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract3, contract4);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, BigDecimal.valueOf(300.30), null, null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByMaxSum() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract1, contract2);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, null, BigDecimal.valueOf(300), null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFilterByMaxSumAtEdge() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract1, contract2, contract3);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, null, BigDecimal.valueOf(300.30), null, null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFiltersByStartDate() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract1, contract2, contract3);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, null, null, new GregorianCalendar(2023, Calendar.JUNE, 13).getTime(),
                null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFiltersByStartDateAtEdge() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract1, contract2, contract3, contract4);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, null, null, new GregorianCalendar(2023, Calendar.JUNE, 1).getTime(),
                null, PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFiltersByEndDate() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract2, contract4);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, null, null, null,
                new GregorianCalendar(2023, Calendar.JUNE, 18).getTime(), PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithFiltersByEndDateAtEdge() {
        List<ContractEntity> expectedContracts = Arrays.asList(contract2, contract4);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(null, null,
                null, null, null, null,
                new GregorianCalendar(2023, Calendar.JUNE, 16).getTime(), PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }

    @Test
    void testFindAllWithAllFilters() {
        List<ContractEntity> expectedContracts = Collections.singletonList(contract1);

        Page<ContractEntity> actualContracts = contractRepository.findAllWithFilters(
                1L,
                "Тестовый контракт",
                ContractType.SUPPLY,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(150.10),
                new GregorianCalendar(2023, Calendar.JUNE, 20).getTime(),
                new GregorianCalendar(2023, Calendar.JULY, 10).getTime(),
                PageRequest.of(0, 10));

        assertThat(actualContracts).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContracts);
    }
}
