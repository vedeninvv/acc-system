package com.practice.accsystem.service;

import com.practice.accsystem.entity.CounterpartyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис для контрагентов
 */
public interface CounterpartyService {
    /**
     * Создать контрагента
     *
     * @param counterparty данные создаваемого контрагента
     * @return созданный контрагент
     */
    CounterpartyEntity createCounterparty(CounterpartyEntity counterparty);

    /**
     * Найти контрагента по ID
     *
     * @param counterpartyId ID контрагента
     * @return контрагент
     */
    CounterpartyEntity findCounterpartyById(Long counterpartyId);

    /**
     * Поиск всех контрагентов с фильтрацией по совпадению переданной строки с хотя бы частью названия, адреса или ИНН
     * Если строка null, то фильтрация не осуществляется
     *
     * @param searchStr поисковая строка
     * @param pageable  настройки пагинации
     * @return контрагенты
     */
    Page<CounterpartyEntity> findAllCounterparties(String searchStr, Pageable pageable);

    /**
     * Обновить контрагента
     *
     * @param oldCounterparty обновляемый контрагент
     * @param newCounterparty данные для обновления контрагента
     * @return обновленный контрагент
     */
    CounterpartyEntity updateCounterparty(CounterpartyEntity oldCounterparty, CounterpartyEntity newCounterparty);

    /**
     * Удалить контрагента
     * Удаление невозможно, если существуют контракты с контрагентом, которые относятся к этому контрагенту
     *
     * @param counterparty удаляемый контрагент
     * @return удаленный контрагент
     */
    CounterpartyEntity deleteCounterparty(CounterpartyEntity counterparty);
}
