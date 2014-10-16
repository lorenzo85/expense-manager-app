package com.spring.cms.web.controller.reports.templates;

import com.spring.cms.web.controller.reports.styles.StyleFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ExcelTemplate<T> {

    String getFilename();

    void build();

    ExcelTemplate<T> setSheet(HSSFSheet sheet);

    ExcelTemplate<T> setHeaderStyle(StyleFactory headerStyle);

    ExcelTemplate<T> setRowStyle(StyleFactory rowStyle);
}
