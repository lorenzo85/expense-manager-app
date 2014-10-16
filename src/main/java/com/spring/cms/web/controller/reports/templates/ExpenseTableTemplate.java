package com.spring.cms.web.controller.reports.templates;

import com.spring.cms.service.dto.ExpenseDto;

import java.util.Collection;

public class ExpenseTableTemplate extends AbstractExcelTableTemplate<ExpenseDto> {

    public ExpenseTableTemplate(Collection<ExpenseDto> entities) {
        super(entities);
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