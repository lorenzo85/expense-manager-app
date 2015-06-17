package org.cms.data.app.rest.reports.styles;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;

import static java.util.Locale.ENGLISH;
import static org.apache.poi.hssf.util.HSSFColor.WHITE;
import static org.apache.poi.ss.usermodel.CellStyle.SOLID_FOREGROUND;
import static org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD;
import static org.apache.poi.ss.util.DateFormatConverter.convert;

public abstract class BaseAbstractStyleFactory implements StyleFactory {

    private static final String FONT_ARIAL = "Arial";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String CURRENCY_FORMAT = "€ #,##0.00";

    protected HSSFWorkbook workbook;

    public BaseAbstractStyleFactory(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public CellStyle getCurrencyStyle() {
        HSSFDataFormat cf = workbook.createDataFormat();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(cf.getFormat(CURRENCY_FORMAT));
        return style;
    }

    @Override
    public CellStyle getDateStyle() {
        String excelFormatPattern = convert(ENGLISH, DATE_FORMAT);
        CellStyle dateStyle = workbook.createCellStyle();
        DataFormat poiFormat = workbook.createDataFormat();
        dateStyle.setDataFormat(poiFormat.getFormat(excelFormatPattern));
        return dateStyle;
    }

    @Override
    public CellStyle getLongStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(WHITE.index);
        style.setFillPattern(SOLID_FOREGROUND);
        return style;
    }

    protected Font createFont(short color) {
        Font font = workbook.createFont();
        font.setFontName(FONT_ARIAL);
        font.setBoldweight(BOLDWEIGHT_BOLD);
        font.setColor(color);
        return font;
    }
}
