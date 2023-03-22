package com.practice.accsystem.repository;

import com.practice.accsystem.entity.CounterpartyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Класс репозитория для контрагентов
 */
@Repository
public interface CounterpartyRepository extends PagingAndSortingRepository<CounterpartyEntity, Long> {
    /**
     * Проверяет, существует ли переданное название или ИНН в БД
     *
     * @param title проверяемое название
     * @param INN   проверяемое ИНН
     * @return true, если существует, иначе false
     */
    Boolean existsByTitleOrINN(String title, String INN);

    /**
     * Поиск всех контрагентов с фильтрацией по переданным значениям
     * Если параметр null, то фильтрация по нему не осуществляется
     *
     * @param title название или его часть
     * @param address адрес или его часть
     * @param INN ИНН или его часть
     * @param pageable  настройки пагинации
     * @return контрагенты
     */
    @Query("select counterparty from CounterpartyEntity as counterparty where " +
            "(:title is null or counterparty.title like %:title%)" +
            " and (counterparty.address like %:address%)" +
            " and (counterparty.INN like %:INN%)")
    Page<CounterpartyEntity> findAllWithFilters(String title, String address, String INN, Pageable pageable);
}
