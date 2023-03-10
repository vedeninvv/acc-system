package com.practice.accsystem.service.impl;

import com.practice.accsystem.entity.ExcelRecord;
import com.practice.accsystem.excel.ExcelExporter;
import com.practice.accsystem.exception.ExcelGenerationException;
import com.practice.accsystem.service.ExcelService;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {
    private static final int DEFAULT_SHEET_INDEX = 0;
    private static final int DEFAULT_ROW_CONTENT_START_INDEX = 1;
    private static final int DEFAULT_COLUMN_CONTENT_START_INDEX = 0;

    private final Map<Class<?>, ExcelExporter> excelExporterMap;

    public ExcelServiceImpl(List<ExcelExporter> excelExporterList) {
        this.excelExporterMap = excelExporterList.stream()
                .collect(Collectors.toMap(ExcelExporter::getExportingType, excelExporter -> excelExporter));
    }

    public Row writeEntityInLine(Row row, ExcelRecord entity, Sheet sheet) throws IOException {
        if (excelExporterMap.containsKey(entity.getClass())) {
            return excelExporterMap.get(entity.getClass()).writeEntity(row, DEFAULT_COLUMN_CONTENT_START_INDEX, entity, sheet);
        } else {
            throw new IOException(String.format("Can not write entity '%s'", entity.getClass().getName()));
        }
    }

    @Override
    public byte[] writeEntitiesInTemplateLineByLine(List<ExcelRecord> entities, Resource template) {
        try (InputStream inputStream = new FileInputStream(template.getFile());
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(DEFAULT_SHEET_INDEX);

            Row row = sheet.createRow(DEFAULT_ROW_CONTENT_START_INDEX);
            for (ExcelRecord entity : entities) {
                row = writeEntityInLine(row, entity, sheet);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new ExcelGenerationException(e.getMessage(), e);
        }
    }
}
