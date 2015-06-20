package org.cms.rest.report.excel.styles;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

public interface StyleFactory {

    CellStyle getStringStyle(HSSFWorkbook workbook);

    CellStyle getCurrencyStyle(HSSFWorkbook workbook);

    CellStyle getDateStyle(HSSFWorkbook workbook);

    CellStyle getLongStyle(HSSFWorkbook workbook);

}
