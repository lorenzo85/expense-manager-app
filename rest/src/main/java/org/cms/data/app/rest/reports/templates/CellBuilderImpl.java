package org.cms.data.app.rest.reports.templates;

import org.cms.data.app.rest.reports.styles.StyleFactory;
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
    public CellBuilder addCell(Money value) {
        HSSFCell cell = row.createCell(cellIndex);
        cell.setCellValue(value.getAmount().doubleValue());
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