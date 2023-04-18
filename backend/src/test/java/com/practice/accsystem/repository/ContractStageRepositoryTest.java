package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
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
public class ContractStageRepositoryTest {
    @Autowired
    private ContractStageRepository contractStageRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private UserRepository userRepository;

    private ContractEntity contract1;
    private ContractEntity contract2;

    private ContractStageEntity contractStage1;
    private ContractStageEntity contractStage2;
    private ContractStageEntity contractStage3;
    private ContractStageEntity contractStage4;

    @BeforeEach
    void prepareContractStages() {
        AppUserEntity user = AppUserEntity.builder().username("user").role(Role.USER).build();
        userRepository.save(user);

        contract1 = contractRepository.save(
                ContractEntity.builder().assignedUser(user).build());
        contract2 = contractRepository.save(
                ContractEntity.builder().assignedUser(user).build());

        contractStage1 = ContractStageEntity.builder()
                .contract(contract1)
                .title("Тестовый этап1 контракта1")
                .sum(BigDecimal.valueOf(100.10))
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 10).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 20).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JULY, 7).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JULY, 12).getTime())
                .build();
        contractStage2 = ContractStageEntity.builder()
                .contract(contract1)
                .title("Тестовый этап2 контракта1")
                .sum(BigDecimal.valueOf(200.20))
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 15).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 16).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JUNE, 15).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JUNE, 16).getTime())
                .build();
        contractStage3 = ContractStageEntity.builder()
                .contract(contract2)
                .title("Тестовый этап1 контракта2")
                .sum(BigDecimal.valueOf(300.30))
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 10).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 20).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JULY, 7).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JULY, 12).getTime())
                .build();
        contractStage4 = ContractStageEntity.builder()
                .contract(contract2)
                .title("Тестовый этап2 контракта2")
                .sum(BigDecimal.valueOf(400.40))
                .planStartDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .planEndDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .factStartDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .factEndDate(new GregorianCalendar(2023, Calendar.JUNE, 1).getTime())
                .build();
        contractStageRepository.saveAll(Arrays.asList(contractStage1, contractStage2, contractStage3, contractStage4));
    }

    @Test
    void testFindAllByContractWithoutContract() {
        List<ContractStageEntity> expectedContractStages = Collections.emptyList();

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(null, null,
                null, null, null, null, null, null,
                null, null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithoutFilters() {
        List<ContractStageEntity> expectedContractStages = Arrays.asList(contractStage1, contractStage2);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(contract1, null,
                null, null, null, null, null, null,
                null, null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByFullTitle() {
        List<ContractStageEntity> expectedContractStages = Collections.singletonList(contractStage1);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(
                contract1, "Тестовый этап1 контракта1", null, null, null, null,
                null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByPartOfTitle() {
        List<ContractStageEntity> expectedContractStages = Arrays.asList(contractStage1, contractStage2);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(
                contract1, "контракта1", null, null, null, null,
                null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByMinSum() {
        List<ContractStageEntity> expectedContractStages = Collections.singletonList(contractStage2);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(
                contract1, null, BigDecimal.valueOf(100.11), null, null, null,
                null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByMinSumAtEdge() {
        List<ContractStageEntity> expectedContractStages = Arrays.asList(contractStage1, contractStage2);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(
                contract1, null, BigDecimal.valueOf(100.10), null, null, null,
                null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByMaxSum() {
        List<ContractStageEntity> expectedContractStages = Arrays.asList(contractStage1, contractStage2);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(
                contract1, null, null, BigDecimal.valueOf(300.11), null, null,
                null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByMaxSumAtEdge() {
        List<ContractStageEntity> expectedContractStages = Collections.singletonList(contractStage1);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(
                contract1, null, null, BigDecimal.valueOf(100.10), null, null,
                null, null, null, null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByStartDate() {
        List<ContractStageEntity> expectedContractStages = Arrays.asList(contractStage1, contractStage2);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(contract1, null,
                null, null, null, null, null, null,
                new GregorianCalendar(2023, Calendar.JUNE, 13).getTime(), null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByStartDateAtEdge() {
        List<ContractStageEntity> expectedContractStages = Collections.singletonList(contractStage3);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(contract2, null,
                null, null, null, null, null, null,
                new GregorianCalendar(2023, Calendar.JULY, 7).getTime(), null, PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByEndDate() {
        List<ContractStageEntity> expectedContractStages = Arrays.asList(contractStage1, contractStage2);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(contract1, null,
                null, null, null, null, null, null,
                null, new GregorianCalendar(2023, Calendar.JUNE, 22).getTime(), PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithFilterByEndDateAtEdge() {
        List<ContractStageEntity> expectedContractStages = Collections.singletonList(contractStage4);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(contract2, null,
                null, null, null, null, null, null,
                null, new GregorianCalendar(2023, Calendar.JUNE, 1).getTime(), PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }

    @Test
    void testFindAllByContractWithAllFilters() {
        List<ContractStageEntity> expectedContractStages = Collections.singletonList(contractStage1);

        Page<ContractStageEntity> actualContractStages = contractStageRepository.findAllByContract(
                contract1,
                "Тестовый этап1 контракта1",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(150.10),
                null, null, null, null,
                new GregorianCalendar(2023, Calendar.JUNE, 10).getTime(),
                new GregorianCalendar(2023, Calendar.JULY, 10).getTime(),
                PageRequest.of(0, 10));

        assertThat(actualContractStages).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedContractStages);
    }
}
