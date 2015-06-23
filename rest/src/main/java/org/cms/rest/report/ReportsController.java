package org.cms.rest.report;

import org.cms.rest.report.excel.ExcelDocumentBuilder;
import org.cms.rest.report.excel.ExcelDocument;
import org.cms.core.expense.ExpenseDto;
import org.cms.core.expense.ExpenseService;
import org.cms.core.income.IncomeDto;
import org.cms.core.income.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ReportsController {

    private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    private static final String HEADER_CONTENT_DISPOSITION_FILE = "attachment;filename=\"%s\"";

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    @Qualifier("expenseTemplate")
    private ExcelDocumentBuilder<ExpenseDto> expenseTemplate;
    @Autowired
    @Qualifier("incomeTemplate")
    private ExcelDocumentBuilder<IncomeDto> incomeTemplate;


    @RequestMapping(value= "/reports/yards/{yardId}/expenses", method= GET)
    public void downloadExpenses(@PathVariable("yardId") long yardId, HttpServletResponse response) throws IOException {
        List<ExpenseDto> expenses = expenseService.listExpensesForYard(yardId);
        ExcelDocument expensesXls = expenseTemplate.entities(expenses).build();
        writeToResponseAndSetHeaders(expensesXls, response);
    }

    @RequestMapping(value= "/reports/yards/{yardId}/incomes", method= GET)
    public void downloadIncomes(@PathVariable("yardId") long yardId, HttpServletResponse response) throws IOException {
        List<IncomeDto> incomes = incomeService.listIncomesForYard(yardId);
        ExcelDocument incomesXls = incomeTemplate.entities(incomes).build();
        writeToResponseAndSetHeaders(incomesXls, response);
    }

    private void writeToResponseAndSetHeaders(ExcelDocument excelDocument, HttpServletResponse response) throws IOException {
        response.setHeader(HEADER_CONTENT_DISPOSITION, format(HEADER_CONTENT_DISPOSITION_FILE, excelDocument.getName()));
        response.getOutputStream().write(excelDocument.asBase64BytesEncoded());
    }
}
