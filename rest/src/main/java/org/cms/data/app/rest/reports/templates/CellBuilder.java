package org.cms.data.app.rest.reports.templates;

import org.joda.money.Money;

import java.util.Date;

public interface CellBuilder {

    CellBuilder addCell(String value);

    CellBuilder addCell(Money value);

    CellBuilder addCell(Date date);

    CellBuilder addCell(Long value);

}
