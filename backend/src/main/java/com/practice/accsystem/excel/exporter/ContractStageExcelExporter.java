package com.practice.accsystem.excel.exporter;

import com.practice.accsystem.dto.contractStage.ContractStageGetDto;
import com.practice.accsystem.entity.ContractStageEntity;
import com.practice.accsystem.entity.ExcelRecord;
import com.practice.accsystem.entity.ExpenseEntity;
import com.practice.accsystem.excel.ExcelExporter;
import com.practice.accsystem.mapper.ContractStageMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ContractStageExcelExporter extends ExcelExporter {
    private static final int CONTRACT_STAGE_CELL_NUMBER = 9;

    private final ContractStageMapper contractStageMapper;
    private final ExpenseExcelExporter expenseExcelExporter;

    public ContractStageExcelExporter(ContractStageMapper contractStageMapper, ExpenseExcelExporter expenseExcelExporter) {
        this.contractStageMapper = contractStageMapper;
        this.expenseExcelExporter = expenseExcelExporter;
    }

    @Override
    public Class<?> getExportingType() {
        return ContractStageEntity.class;
    }

    @Override
    public Row writeEntity(Row row, int columnIndex, ExcelRecord entity, Sheet sheet) throws IOException {
        row = super.writeEntity(row, columnIndex, entity, sheet);

        ContractStageEntity contractStage = (ContractStageEntity) entity;
        ContractStageGetDto contractStageDto = contractStageMapper.toDto(contractStage);

        Cell[] cells = generateCells(row, columnIndex, CONTRACT_STAGE_CELL_NUMBER);

        final int START_CELL_INDEX = 0;
        cells[START_CELL_INDEX].setCellValue(contractStageDto.getId());
        cells[START_CELL_INDEX + 1].setCellValue(contractStageDto.getTitle());
        cells[START_CELL_INDEX + 2].setCellValue(contractStageDto.getSum().toString());
        cells[START_CELL_INDEX + 3].setCellValue(contractStageDto.getPlanTotalExpenses().toString());
        cells[START_CELL_INDEX + 4].setCellValue(contractStageDto.getFactTotalExpenses().toString());
        cells[START_CELL_INDEX + 5].setCellValue(contractStageDto.getPlanStartDate());
        cells[START_CELL_INDEX + 6].setCellValue(contractStageDto.getPlanEndDate());
        cells[START_CELL_INDEX + 7].setCellValue(contractStageDto.getFactStartDate());
        cells[START_CELL_INDEX + 8].setCellValue(contractStageDto.getFactEndDate());

        int expensesNum = contractStage.getExpenses().size();
        if (expensesNum > 1) {
            mergeCellsForContractStage(sheet, row.getRowNum(), row.getRowNum() + expensesNum - 1, columnIndex);
        }

        for (ExpenseEntity expense : contractStage.getExpenses()) {
            row = expenseExcelExporter.writeEntity(row, CONTRACT_STAGE_CELL_NUMBER, expense, sheet);
        }

        return row;
    }

    private void mergeCellsForContractStage(Sheet sheet, int startRowIndex, int endRowIndex, int startColumnIndex) {
        for (int columnIndex = startColumnIndex; columnIndex < startColumnIndex + CONTRACT_STAGE_CELL_NUMBER; columnIndex++) {
            sheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, columnIndex, columnIndex));
        }
    }
}
