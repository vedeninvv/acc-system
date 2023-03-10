package com.practice.accsystem.excel.exporter;

import com.practice.accsystem.dto.counterpartyContract.CounterpartyContractGetDto;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.ExcelRecord;
import com.practice.accsystem.excel.ExcelExporter;
import com.practice.accsystem.mapper.CounterpartyContractMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CounterpartyContractExcelExporter extends ExcelExporter {
    private final static int COUNTERPARTY_CONTRACT_CELL_NUMBER = 10;
    private static final String CONTRACT_TYPE = "Договор с контрагентом";

    private final CounterpartyContractMapper counterpartyContractMapper;

    public CounterpartyContractExcelExporter(CounterpartyContractMapper counterpartyContractMapper) {
        this.counterpartyContractMapper = counterpartyContractMapper;
    }

    @Override
    public Class<?> getExportingType() {
        return CounterpartyContractEntity.class;
    }

    @Override
    public Row writeEntity(Row row, int columnIndex, ExcelRecord entity, Sheet sheet) throws IOException {
        row = super.writeEntity(row, columnIndex, entity, sheet);

        CounterpartyContractGetDto counterpartyContractDto = counterpartyContractMapper.toDto((CounterpartyContractEntity) entity);

        Cell[] cells = generateCells(row, columnIndex, COUNTERPARTY_CONTRACT_CELL_NUMBER);

        final int START_CELL_INDEX = 0;
        cells[START_CELL_INDEX].setCellValue(counterpartyContractDto.getId());
        cells[START_CELL_INDEX + 1].setCellValue(counterpartyContractDto.getTitle());
        cells[START_CELL_INDEX + 2].setCellValue(counterpartyContractDto.getContractType().toString());
        cells[START_CELL_INDEX + 3].setCellValue(counterpartyContractDto.getSum().toString());
        cells[START_CELL_INDEX + 4].setCellValue(counterpartyContractDto.getPlanStartDate());
        cells[START_CELL_INDEX + 5].setCellValue(counterpartyContractDto.getPlanEndDate());
        cells[START_CELL_INDEX + 6].setCellValue(counterpartyContractDto.getFactStartDate());
        cells[START_CELL_INDEX + 7].setCellValue(counterpartyContractDto.getFactEndDate());
        cells[START_CELL_INDEX + 8].setCellValue(CONTRACT_TYPE);
        cells[START_CELL_INDEX + 9].setCellValue(counterpartyContractDto.getContractId());

        return sheet.createRow(row.getRowNum() + 1);
    }
}
