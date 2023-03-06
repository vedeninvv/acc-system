package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.CounterpartyEntity;
import com.practice.accsystem.exception.DuplicateUniqueValueException;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.exception.RelatedEntitiesCanNotBeDeleted;
import com.practice.accsystem.repository.CounterpartyRepository;
import com.practice.accsystem.service.CounterpartyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CounterpartyServiceImpl implements CounterpartyService {
    private final CounterpartyRepository counterpartyRepository;

    public CounterpartyServiceImpl(CounterpartyRepository counterpartyRepository) {
        this.counterpartyRepository = counterpartyRepository;
    }

    @Override
    public CounterpartyEntity createCounterparty(CounterpartyEntity counterparty) {
        if (counterpartyRepository.existsByTitleAndINN(counterparty.getTitle(), counterparty.getINN())) {
            throw new DuplicateUniqueValueException(
                    String.format("Title '%s' or INN '%s' already exist when try to create counterparty",
                            counterparty.getTitle(), counterparty.getINN()));
        }

        return counterpartyRepository.save(counterparty);
    }

    @Override
    public CounterpartyEntity findCounterpartyById(Long counterpartyId) {
        return counterpartyRepository.findById(counterpartyId).orElseThrow(() ->
                new NotFoundEntityException(String.format("Counterparty not found by id '%d'", counterpartyId)));
    }

    @Override
    public Page<CounterpartyEntity> findAllCounterparties(String searchStr, Pageable pageable) {
        return counterpartyRepository.findAllBySearchStr(searchStr, pageable);
    }

    @Override
    public CounterpartyEntity updateCounterparty(CounterpartyEntity oldCounterparty, CounterpartyEntity newCounterparty) {

        oldCounterparty.setTitle(newCounterparty.getTitle());
        oldCounterparty.setAddress(newCounterparty.getAddress());
        oldCounterparty.setINN(newCounterparty.getINN());

        return oldCounterparty;
    }

    @Override
    public CounterpartyEntity deleteCounterparty(CounterpartyEntity counterparty) {
        if (!counterparty.getCounterpartyContracts().isEmpty()) {
            throw new RelatedEntitiesCanNotBeDeleted("Counterparty", "CounterpartyContract");
        }

        counterpartyRepository.delete(counterparty);
        return counterparty;
    }
}
