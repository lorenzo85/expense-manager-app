package com.spring.cms.web.controller.reports.templates;

import com.spring.cms.service.dto.IncomeDto;

import java.util.Collection;

public class IncomeTableTemplate extends AbstractExcelTableTemplate<IncomeDto> {

    public IncomeTableTemplate(Collection<IncomeDto> entities) {
        super(entities);
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