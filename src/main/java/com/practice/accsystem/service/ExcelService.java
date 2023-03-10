package com.practice.accsystem.service;

import com.practice.accsystem.entity.ExcelRecord;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Сервис для записи данных в Excel
 */
public interface ExcelService {
    /**
     * Запись списка сущностей в excel-файл, используя шаблон.
     * Шаблон - excel-файл, в котором первая строка - это заголовки, все остальные - пустые.
     * Сущности записываются строка за строкой. Особенности записи сущности определяется экспортером
     *
     * @param entities список записываемых сущностей
     * @param template шаблон, используемый для записи
     * @return сгенерированный excel-файл в виде массива байт
     */
    byte[] writeEntitiesInTemplateLineByLine(List<ExcelRecord> entities, Resource template);
}
