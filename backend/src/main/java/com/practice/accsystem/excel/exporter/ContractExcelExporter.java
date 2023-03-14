package com.practice.accsystem.excel.exporter;

import com.practice.accsystem.dto.contract.ContractGetDto;
import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ExcelRecord;
import com.practice.accsystem.excel.ExcelExporter;
import com.practice.accsystem.mapper.ContractMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ContractExcelExporter extends ExcelExporter {
    private static final String CONTRACT_TYPE = "Основной";
    private static final int CONTRACT_CELL_NUMBER = 9;

    private final ContractMapper contractMapper;

    public ContractExcelExporter(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    @Override
    public Class<?> getExportingType() {
        return ContractEntity.class;
    }

    @Override
    public Row writeEntity(Row row, int columnIndex, ExcelRecord entity, Sheet sheet) throws IOException {
        row = super.writeEntity(row, columnIndex, entity, sheet);

        ContractGetDto contractDto = contractMapper.toDto((ContractEntity) entity);

        Cell[] cells = generateCells(row, columnIndex, CONTRACT_CELL_NUMBER);

        final int START_CELL_INDEX = 0;
        cells[START_CELL_INDEX].setCellValue(contractDto.getId());
        cells[START_CELL_INDEX + 1].setCellValue(contractDto.getTitle());
        cells[START_CELL_INDEX + 2].setCellValue(contractDto.getContractType().toString());
        cells[START_CELL_INDEX + 3].setCellValue(contractDto.getSum().toString());
        cells[START_CELL_INDEX + 4].setCellValue(contractDto.getPlanStartDate());
        cells[START_CELL_INDEX + 5].setCellValue(contractDto.getPlanEndDate());
        cells[START_CELL_INDEX + 6].setCellValue(contractDto.getFactStartDate());
        cells[START_CELL_INDEX + 7].setCellValue(contractDto.getFactEndDate());
        cells[START_CELL_INDEX + 8].setCellValue(CONTRACT_TYPE);

        return sheet.createRow(row.getRowNum() + 1);
    }
}
