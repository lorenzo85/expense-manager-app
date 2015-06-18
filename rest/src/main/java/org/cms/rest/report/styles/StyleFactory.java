package org.cms.rest.report.styles;

import org.apache.poi.ss.usermodel.CellStyle;

public interface StyleFactory {

    CellStyle getStringStyle();

    CellStyle getCurrencyStyle();

    CellStyle getDateStyle();

    CellStyle getLongStyle();
}
