package com.practice.accsystem.service;

import com.practice.accsystem.entity.CounterpartyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CounterpartyService {
    CounterpartyEntity createCounterparty(CounterpartyEntity counterparty);

    CounterpartyEntity findCounterpartyById(Long counterpartyId);

    Page<CounterpartyEntity> findAllCounterparties(String searchStr, Pageable pageable);

    CounterpartyEntity updateCounterparty(CounterpartyEntity oldCounterparty, CounterpartyEntity newCounterparty);

    CounterpartyEntity deleteCounterparty(CounterpartyEntity counterparty);
}
