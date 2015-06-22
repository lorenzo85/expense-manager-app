package org.cms.rest.report;

import org.cms.rest.report.excel.AbstractExcelDocumentBuilder;
import org.cms.rest.report.excel.cell.CellBuilder;
import org.cms.rest.report.excel.styles.StyleFactory;
import org.cms.core.expense.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("expenseTemplate")
public class ExpenseExcelDocumentBuilder extends AbstractExcelDocumentBuilder<ExpenseDto> {


    @Autowired
    public ExpenseExcelDocumentBuilder(
            @Qualifier("headerStyleFactory") StyleFactory headerStyle,
            @Qualifier("rowStyleFactory") StyleFactory rowStyle) {
        super(headerStyle, rowStyle);
    }

    @Override
    protected String getDocumentName() {
        return "expenses";
    }

    @Override
    public void fillHeader(CellBuilder cellBuilder) {
        cellBuilder
                .addCell("Title")
                .addCell("Amount")
                .addCell("Invoice Id")
                .addCell("Expires At")
                .addCell("Emitted At");
    }

    @Override
    public void fillRow(CellBuilder cellBuilder, ExpenseDto dto) {
        cellBuilder
                .addCell(dto.getTitle())
                .addCell(dto.getAmount())
                .addCell(dto.getInvoiceId())
                .addCell(dto.getExpiresAt())
                .addCell(dto.getEmissionAt());
    }
}