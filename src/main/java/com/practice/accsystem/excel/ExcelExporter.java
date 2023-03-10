package com.practice.accsystem.excel;

import com.practice.accsystem.entity.ExcelRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

public abstract class ExcelExporter {
    private final static int DEFAULT_ROWS_WRITTEN = 0;

    public abstract Class<?> getExportingType();

    public Row writeEntity(Row row, int columnIndex, ExcelRecord entity, Sheet sheet) throws IOException {
        if (!entity.getClass().equals(getExportingType())) {
            throw new IOException(
                    String.format("Wrong exporting class in ExcelExporter. Got '%s', but expected '%s')", entity.getClass(), getExportingType()));
        }

        return sheet.getRow(row.getRowNum() + DEFAULT_ROWS_WRITTEN);
    }

    protected Cell[] generateCells(Row row, int startColumnIndex, int cellNumber) {
        Cell[] cells = new Cell[cellNumber];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = row.createCell(startColumnIndex + i);
        }

        return cells;
    }
}
