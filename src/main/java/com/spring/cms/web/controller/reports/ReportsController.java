package com.spring.cms.web.controller.reports;

import com.spring.cms.service.dto.ExpenseDto;
import com.spring.cms.service.dto.IncomeDto;
import com.spring.cms.service.expense.ExpenseService;
import com.spring.cms.service.income.IncomeService;
import com.spring.cms.web.controller.reports.templates.ExcelTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ReportsController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ApplicationContext context;

    @RequestMapping(value= "/reports/yards/{yardId}/expenses", method= GET)
    public ModelAndView downloadExpenses(@PathVariable("yardId") long yardId) {
        List<ExpenseDto> expenses = expenseService.listExpensesForYard(yardId);

        ExcelTemplate template = (ExcelTemplate) context.getBean("expenseTemplate", expenses);
        View view = getExcelBuilder(template);
        return new ModelAndView(view);
    }

    @RequestMapping(value= "/reports/yards/{yardId}/incomes", method= GET)
    public ModelAndView downloadIncomes(@PathVariable("yardId") long yardId) {
        List<IncomeDto> incomes = incomeService.listIncomesForYard(yardId);

        ExcelTemplate template = (ExcelTemplate) context.getBean("incomeTemplate", incomes);
        View view = getExcelBuilder(template);
        return new ModelAndView(view);
    }

    private View getExcelBuilder(ExcelTemplate template) {
        return (View) context.getBean("excelBuilder", template);
    }
}
