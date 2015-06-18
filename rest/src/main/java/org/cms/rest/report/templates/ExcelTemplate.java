package org.cms.rest.report.templates;

import org.cms.rest.report.styles.StyleFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ExcelTemplate<T> {

    String getFilename();

    void build();

    ExcelTemplate<T> setSheet(HSSFSheet sheet);

    ExcelTemplate<T> setHeaderStyle(StyleFactory headerStyle);

    ExcelTemplate<T> setRowStyle(StyleFactory rowStyle);
}
