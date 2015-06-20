package org.cms.rest.report.excel.cell;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.cms.rest.report.excel.styles.StyleFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.joda.money.Money;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("cellBuilder")
@Scope("prototype")
public class CellBuilderImpl implements CellBuilder {

    private int cellIndex;
    private HSSFRow row;
    private HSSFWorkbook workbook;
    private StyleFactory style;

    public CellBuilderImpl(HSSFRow row, HSSFWorkbook workbook, StyleFactory style) {
        this.cellIndex = 0;
        this.row = row;
        this.style = style;
        this.workbook = workbook;
    }

    @Override
    public CellBuilder addCell(String value) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style.getStringStyle(workbook));
        cellIndex++;
        return this;
    }

    @Override
    public CellBuilder addCell(Money value) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(value.getAmount().doubleValue());
        cell.setCellStyle(style.getCurrencyStyle(workbook));
        cellIndex++;
        return this;
    }

    @Override
    public CellBuilder addCell(Date date) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(date);
        cell.setCellStyle(style.getDateStyle(workbook));
        cellIndex++;
        return this;
    }

    @Override
    public CellBuilder addCell(Long value) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style.getLongStyle(workbook));
        cellIndex++;
        return this;
    }
}