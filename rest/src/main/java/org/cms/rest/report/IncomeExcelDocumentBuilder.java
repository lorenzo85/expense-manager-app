package org.cms.rest.report;

import org.cms.rest.report.excel.AbstractExcelDocumentBuilder;
import org.cms.rest.report.excel.cell.CellBuilder;
import org.cms.rest.report.excel.styles.StyleFactory;
import org.cms.service.income.IncomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("incomeTemplate")
public class IncomeExcelDocumentBuilder extends AbstractExcelDocumentBuilder<IncomeDto> {

    @Autowired
    public IncomeExcelDocumentBuilder(
            @Qualifier("headerStyleFactory") StyleFactory headerStyle,
            @Qualifier("rowStyleFactory") StyleFactory rowStyle) {
        super(headerStyle, rowStyle);
    }

    @Override
    protected String getDocumentName() {
        return "incomes";
    }

    @Override
    protected void fillHeader(CellBuilder cellBuilder) {
        cellBuilder
                .addCell("Amount")
                .addCell("Invoice Id")
                .addCell("Status");
    }

    @Override
    protected void fillRow(CellBuilder builder, IncomeDto entity) {
        builder
                .addCell(entity.getAmount())
                .addCell(entity.getInvoiceId())
                .addCell(entity.getStatus().toString());
    }
}