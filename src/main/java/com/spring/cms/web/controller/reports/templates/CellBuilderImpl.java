package com.spring.cms.web.controller.reports.templates;

import com.spring.cms.web.controller.reports.styles.StyleFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.math.BigDecimal;
import java.util.Date;

public class CellBuilderImpl implements CellBuilder {

    private int cellIndex;
    private HSSFRow row;
    private StyleFactory style;

    public CellBuilderImpl(HSSFRow row, StyleFactory style) {
        this.row = row;
        this.style = style;
        this.cellIndex = 0;
    }

    @Override
    public CellBuilder addCell(String value) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style.getStringStyle());
        cellIndex++;
        return this;
    }

    @Override
    public CellBuilder addCell(BigDecimal value) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(value.doubleValue());
        cell.setCellStyle(style.getCurrencyStyle());
        cellIndex++;
        return this;
    }

    @Override
    public CellBuilder addCell(Date date) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(date);
        cell.setCellStyle(style.getDateStyle());
        cellIndex++;
        return this;
    }

    @Override
    public CellBuilder addCell(Long value) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style.getLongStyle());
        cellIndex++;
        return this;
    }
}