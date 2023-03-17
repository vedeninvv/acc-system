package com.practice.accsystem.service;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Сервис для контрактов
 */
public interface ContractService {
    /**
     * Создать контракт
     *
     * @param user     пользователь, который будет назначен на контракт
     * @param contract данные создаваемого контракта
     * @return созданный контракт
     */
    ContractEntity createContract(AppUserEntity user, ContractEntity contract);

    /**
     * Найти контракт по ID
     *
     * @param contractId ID контракта
     * @return контракт с переданным ID
     */
    ContractEntity findContractById(Long contractId);

    /**
     * Найти все контракты, удовлетворяющие переданным критериям.
     * Если критерии не заданы (null), то фильтрация по ним не осуществляется
     *
     * @param assignedUserId ID назначенного пользователя
     * @param title          название контракта или часть названия
     * @param contractType   тип контракта
     * @param minSum         минимальное значение для суммы контракта
     * @param maxSum         максимальное значение для суммы контракта
     * @param startPeriod    дата начала периода для плановой или фактической даты
     *                       (достаточно, чтобы в период входила хотя бы одна)
     * @param endPeriod      дата конца периода для плановой или фактической даты
     *                       (достаточно, чтобы в период входила хотя бы одна)
     * @param pageable       настройки пагинации
     * @return контракты, удовлетворяющие критериям
     */
    Page<ContractEntity> findAllContracts(Long assignedUserId,
                                          String title,
                                          ContractType contractType,
                                          BigDecimal minSum,
                                          BigDecimal maxSum,
                                          Date startPeriod,
                                          Date endPeriod,
                                          Pageable pageable);

    /**
     * Найти все контракты пользователя, планируемые сроки которого входят в заданный период
     *
     * @param user        назначенный на контракт пользователь, по которому происходит фильтрация
     * @param periodStart дата начала периода, в который должны входить планируемые сроки контракта
     * @param periodEnd   дата конца периода, в который должны входить планируемые сроки контракта
     * @return список контрактов
     */
    List<ContractEntity> findAllContractsByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd);

    /**
     * Обновить контракт
     *
     * @param oldContract контракт, который нужно обновить
     * @param newContract данные для обновления контракта
     * @return обновленный контракт
     */
    ContractEntity updateContract(ContractEntity oldContract, ContractEntity newContract);

    /**
     * Удалить контракт
     * Также удаляются контракты с контрагентами и этапы контракта, которые относятся к удаляемому контракту
     *
     * @param contract удаляемый контракт
     * @return контракт, который был удален
     */
    ContractEntity deleteContract(ContractEntity contract);

    /**
     * Проверить, может ли пользователь получить доступ к контракту и работать с ним (читать, изменять, удалять и т.д.)
     *
     * @param user     пользователь, который пытается получить доступ
     * @param contract контракт, к которому получают доступ
     * @return true, если пользователь имеет доступ, иначе false
     */
    boolean hasAccessToContract(AppUserEntity user, ContractEntity contract);

    /**
     * Создать excel-отчет, содержащий все контракты пользователя в заданном периоде по планируемым срокам
     *
     * @param user        пользователь, чьи контракты лобавляются в отчет
     * @param periodStart дата начала периода планируемых сроков
     * @param periodEnd   дата конца периода планируемых сроков
     * @return excel-отчет в виде массива байт
     */
    ByteArrayResource createContractsReportByUserInPeriodByPlanDeadline(AppUserEntity user, Date periodStart, Date periodEnd);

    /**
     * Создать excel-отчет, содержащий все этапы контракта
     *
     * @param contract контракт, чьи этапы должны быть добавлены в отчет
     * @return excel-отчет в виде массива байт
     */
    ByteArrayResource createContractStageReport(ContractEntity contract);
}
