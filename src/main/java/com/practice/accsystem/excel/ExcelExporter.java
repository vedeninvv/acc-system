package com.practice.accsystem.excel;

import com.practice.accsystem.entity.ExcelRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

/**
 * Экспортер сущности в excel-файл
 */
public abstract class ExcelExporter {
    private final static int DEFAULT_ROWS_WRITTEN = 0;

    /**
     * Экспортируемый тип класса сущности
     *
     * @return экспортируемый класс
     */
    public abstract Class<?> getExportingType();

    /**
     * Записывает сущность в лист excel-файла, начиная с переданной строки и индекса колонки
     *
     * @param row         строка, с которой начнется запись
     * @param columnIndex колонка, с которой начнется запись
     * @param entity      экспортируемая сущность
     * @param sheet       лист excel-файла
     * @return следующая пустая строка после записанной сущности
     * @throws IOException если переданная сущность не соответствует экспортируемому типу экспортера
     */
    public Row writeEntity(Row row, int columnIndex, ExcelRecord entity, Sheet sheet) throws IOException {
        if (!entity.getClass().equals(getExportingType())) {
            throw new IOException(
                    String.format("Wrong exporting class in ExcelExporter. Got '%s', but expected '%s')", entity.getClass(), getExportingType()));
        }

        return sheet.getRow(row.getRowNum() + DEFAULT_ROWS_WRITTEN);
    }

    /**
     * Генерирует заданное число пустых ячеек в переданной строке, начиная с переданного индекса и возвращает их в виде массива
     * @param row строка, в которой генерируются ячейки
     * @param startColumnIndex индекс колонки, с которой начинают генерироваться ячейки
     * @param cellNumber количество генерируемых ячеек
     * @return массив сгенерированных ячеек
     */
    protected Cell[] generateCells(Row row, int startColumnIndex, int cellNumber) {
        Cell[] cells = new Cell[cellNumber];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = row.createCell(startColumnIndex + i);
        }

        return cells;
    }
}
