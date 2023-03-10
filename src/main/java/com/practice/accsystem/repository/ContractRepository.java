package com.practice.accsystem.repository;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ContractRepository extends PagingAndSortingRepository<ContractEntity, Long> {
    @Query("select contract from ContractEntity as contract where" +
            " (:assignedUserId is null or contract.assignedUser.id = :assignedUserId) " +
            " and (:title is null or contract.title like %:title%)" +
            " and (:contractType is null or contract.contractType = :contractType)" +
            " and (:minSum is null or contract.sum >= :minSum)" +
            " and (:maxSum is null  or contract.sum <= :maxSum)")
    Page<ContractEntity> findAllWithFilters(Long assignedUserId,
                                            String title,
                                            ContractType contractType,
                                            BigDecimal minSum,
                                            BigDecimal maxSum,
                                            Pageable pageable);

    List<ContractEntity> findAllByAssignedUserAndPlanStartDateAfterAndPlanEndDateBefore(AppUserEntity user,
                                                                                        Date periodStart,
                                                                                        Date periodEnd);
}
