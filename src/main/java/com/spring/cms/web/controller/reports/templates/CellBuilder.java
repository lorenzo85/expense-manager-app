package com.spring.cms.web.controller.reports.templates;

import java.math.BigDecimal;
import java.util.Date;

public interface CellBuilder {

    CellBuilder addCell(String value);

    CellBuilder addCell(BigDecimal value);

    CellBuilder addCell(Date date);

    CellBuilder addCell(Long value);

}
