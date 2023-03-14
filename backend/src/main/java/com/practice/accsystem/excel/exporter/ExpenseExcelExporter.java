package com.practice.accsystem.excel.exporter;

import com.practice.accsystem.dto.contractStage.ContractStageGetDto;
import com.practice.accsystem.dto.expense.ExpenseGetDto;
import com.practice.accsystem.entity.ContractStageEntity;
import com.practice.accsystem.entity.ExcelRecord;
import com.practice.accsystem.entity.ExpenseEntity;
import com.practice.accsystem.excel.ExcelExporter;
import com.practice.accsystem.mapper.ExpenseMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExpenseExcelExporter extends ExcelExporter {
    private static final int EXPENSE_CELL_NUMBER = 4;

    private final ExpenseMapper expenseMapper;

    public ExpenseExcelExporter(ExpenseMapper expenseMapper) {
        this.expenseMapper = expenseMapper;
    }

    @Override
    public Class<?> getExportingType() {
        return ExpenseEntity.class;
    }

    @Override
    public Row writeEntity(Row row, int columnIndex, ExcelRecord entity, Sheet sheet) throws IOException {
        row = super.writeEntity(row, columnIndex, entity, sheet);

        ExpenseGetDto expenseDto = expenseMapper.toDto((ExpenseEntity) entity);

        Cell[] cells = generateCells(row, columnIndex, EXPENSE_CELL_NUMBER);

        final int START_CELL_INDEX = 0;
        cells[START_CELL_INDEX].setCellValue(expenseDto.getId());
        cells[START_CELL_INDEX + 1].setCellValue(expenseDto.getTitle());
        cells[START_CELL_INDEX + 2].setCellValue(expenseDto.getPlanAmount().toString());
        cells[START_CELL_INDEX + 3].setCellValue(expenseDto.getFactAmount().toString());

        return sheet.createRow(row.getRowNum() + 1);
    }
}
