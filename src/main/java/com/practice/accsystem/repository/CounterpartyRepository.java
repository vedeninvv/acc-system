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
     * Поиск всех контрагентов с фильтрацией по совпадению переданной строки с хотя бы частью названия, адреса или ИНН
     * Если строка null, то фильтрация не осуществляется
     *
     * @param searchStr поисковая строка
     * @param pageable  настройки пагинации
     * @return контрагенты
     */
    @Query("select counterparty from CounterpartyEntity as counterparty where " +
            ":searchStr is null " +
            "or counterparty.title like %:searchStr% " +
            "or counterparty.address like %:searchStr% " +
            "or counterparty.INN like %:searchStr%")
    Page<CounterpartyEntity> findAllBySearchStr(String searchStr, Pageable pageable);
}
