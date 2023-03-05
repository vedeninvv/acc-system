package com.practice.accsystem.repository;

import com.practice.accsystem.entity.CounterpartyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterpartyRepository extends PagingAndSortingRepository<CounterpartyEntity, Long> {
    Boolean existsByTitleAndINN(String title, String INN);

    @Query("select counterparty from CounterpartyEntity as counterparty where " +
            ":searchStr is null " +
            "or counterparty.title like %:searchStr% " +
            "or counterparty.address like %:searchStr% " +
            "or counterparty.INN like %:searchStr%")
    Page<CounterpartyEntity> findAllBySearchStr(String searchStr, Pageable pageable);
}
