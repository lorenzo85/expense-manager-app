package com.spring.cms.web.controller.reports.styles;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import static org.apache.poi.hssf.util.HSSFColor.BLACK;
import static org.apache.poi.hssf.util.HSSFColor.WHITE;
import static org.apache.poi.ss.usermodel.CellStyle.SOLID_FOREGROUND;

public class RowStyleFactory extends BaseAbstractStyleFactory {

    public RowStyleFactory(HSSFWorkbook workbook) {
        super(workbook);
    }

    @Override
    public CellStyle getStringStyle() {
        Font font = createFont(BLACK.index);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(WHITE.index);
        style.setFillPattern(SOLID_FOREGROUND);
        style.setFont(font);
        return style;
    }
}