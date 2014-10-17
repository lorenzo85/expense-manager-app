package com.spring.cms.web.controller.reports.styles;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.apache.poi.hssf.util.HSSFColor.GREEN;
import static org.apache.poi.hssf.util.HSSFColor.WHITE;
import static org.apache.poi.ss.usermodel.CellStyle.SOLID_FOREGROUND;

@Component("headerStyleFactory")
@Scope("prototype")
public class HeaderStyleFactory extends BaseAbstractStyleFactory {

    public HeaderStyleFactory(HSSFWorkbook workbook) {
        super(workbook);
    }

    @Override
    public CellStyle getStringStyle() {
        Font font = createFont(WHITE.index);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(GREEN.index);
        style.setFillPattern(SOLID_FOREGROUND);
        style.setFont(font);
        return style;
    }
}
