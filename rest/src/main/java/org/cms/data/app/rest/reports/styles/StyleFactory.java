package org.cms.data.app.rest.reports.styles;

import org.apache.poi.ss.usermodel.CellStyle;

public interface StyleFactory {

    CellStyle getStringStyle();

    CellStyle getCurrencyStyle();

    CellStyle getDateStyle();

    CellStyle getLongStyle();
}
