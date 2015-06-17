package org.cms.data.app.rest.reports.templates;

import org.cms.data.app.rest.reports.styles.StyleFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ExcelTemplate<T> {

    String getFilename();

    void build();

    ExcelTemplate<T> setSheet(HSSFSheet sheet);

    ExcelTemplate<T> setHeaderStyle(StyleFactory headerStyle);

    ExcelTemplate<T> setRowStyle(StyleFactory rowStyle);
}
