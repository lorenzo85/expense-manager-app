package org.cms.rest.report.templates;

import org.cms.rest.report.styles.StyleFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public abstract class AbstractExcelTableTemplate<T> implements ExcelTemplate<T> {

    @Autowired
    private ApplicationContext context;

    private static final String REPORT_NAME = "%s-%s.xls";
    private static final String FILE_NAME_DATE_FORMAT = "dd-MM-yyy hh.mm";

    private int rowIndex;
    private HSSFSheet sheet;
    private StyleFactory rowStyle;
    private Collection<T> entities;
    private StyleFactory headerStyle;

    public AbstractExcelTableTemplate(Collection<T> entities) {
        this.rowIndex = 0;
        this.entities = entities;
    }

    @Override
    public void build() {
        checkNotNull(sheet);
        checkNotNull(rowStyle);
        checkNotNull(headerStyle);

        createHeader();
        createRows();
    }

    @Override
    public AbstractExcelTableTemplate<T> setSheet(HSSFSheet sheet) {
        this.sheet = sheet;
        return this;
    }

    @Override
    public AbstractExcelTableTemplate<T> setHeaderStyle(StyleFactory headerStyle) {
        this.headerStyle = headerStyle;
        return this;
    }

    @Override
    public AbstractExcelTableTemplate<T> setRowStyle(StyleFactory rowStyle) {
        this.rowStyle = rowStyle;
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

    private void createHeader() {
        CellBuilder cellBuilder = createCellBuilder(headerStyle);
        fillHeader(cellBuilder);
    }

    private void createRows() {
        entities.forEach(entity -> {
            CellBuilder builder = createCellBuilder(rowStyle);
            fillRow(builder, entity);
        });
    }

    private CellBuilder createCellBuilder(StyleFactory factory) {
        return (CellBuilder) context.getBean("cellBuilder", createRow(), factory);
    }

    private HSSFRow createRow() {
        HSSFRow row = sheet.createRow(rowIndex);
        rowIndex++;
        return row;
    }
}