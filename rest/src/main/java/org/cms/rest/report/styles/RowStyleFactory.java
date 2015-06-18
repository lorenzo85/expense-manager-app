package org.cms.rest.report.styles;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.apache.poi.hssf.util.HSSFColor.BLACK;
import static org.apache.poi.hssf.util.HSSFColor.WHITE;
import static org.apache.poi.ss.usermodel.CellStyle.SOLID_FOREGROUND;

@Component("rowStyleFactory")
@Scope("prototype")
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