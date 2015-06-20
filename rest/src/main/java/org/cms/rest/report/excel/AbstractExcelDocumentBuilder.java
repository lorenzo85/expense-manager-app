package org.cms.rest.report.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.cms.rest.report.excel.cell.CellBuilder;
import org.cms.rest.report.excel.cell.CellBuilderImpl;
import org.cms.rest.report.excel.styles.StyleFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;


public abstract class AbstractExcelDocumentBuilder<T> implements ExcelDocumentBuilder<T> {

    private static final int DEFAULT_COLUMN_WIDTH = 20;
    private static final String REPORT_NAME = "%s-%s.xls";
    private static final String FILE_NAME_DATE_FORMAT = "dd-MM-yyy hh.mm";

    private int rowIndex;
    private HSSFSheet sheet;
    private HSSFWorkbook workbook;
    private StyleFactory rowStyle;
    private Collection<T> entities;
    private StyleFactory headerStyle;

    public AbstractExcelDocumentBuilder(StyleFactory headerStyle, StyleFactory rowStyle) {
        this.rowIndex = 0;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet(getFilename());
        this.sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
        this.rowStyle = rowStyle;
        this.headerStyle = headerStyle;
    }

    @Override
    public ExcelDocument build() throws IOException {
        checkNotNull(rowStyle);
        checkNotNull(entities);
        checkNotNull(headerStyle);
        buildHeader();
        buildRows();
        return new ExcelDocument(getFilename(), workbook);
    }

    @Override
    public AbstractExcelDocumentBuilder<T> setEntities(Collection<T> entities) {
        this.entities = entities;
        return this;
    }

    @Override
    public String getFilename() {
        DateFormat formatter = new SimpleDateFormat(FILE_NAME_DATE_FORMAT);
        return format(REPORT_NAME, getDocumentName(), formatter.format(new Date()));
    }

    protected abstract String getDocumentName();

    protected abstract void fillHeader(CellBuilder cellBuilder);

    protected abstract void fillRow(CellBuilder builder, T entity);

    private void buildHeader() {
        HSSFRow row = createRow();
        CellBuilder cellBuilder = new CellBuilderImpl(row, workbook, headerStyle);
        fillHeader(cellBuilder);
    }

    private void buildRows() {
        entities.forEach(entity -> {
            HSSFRow row = createRow();
            CellBuilder builder = new CellBuilderImpl(row, workbook, rowStyle);
            fillRow(builder, entity);
        });
    }

    private HSSFRow createRow() {
        HSSFRow row = sheet.createRow(rowIndex);
        rowIndex++;
        return row;
    }
}