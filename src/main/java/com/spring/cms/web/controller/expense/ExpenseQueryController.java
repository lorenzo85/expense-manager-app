package com.spring.cms.web.controller.expense;

import com.spring.cms.persistence.domain.ExpenseCategory;
import com.spring.cms.persistence.domain.PaymentState;
import com.spring.cms.service.dto.DeadlinesDto;
import com.spring.cms.service.dto.ExpenseCategoryDto;
import com.spring.cms.service.dto.ExpenseDto;
import com.spring.cms.service.dto.PaymentStateDto;
import com.spring.cms.service.expense.ExpenseAnalysisService;
import com.spring.cms.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ExpenseQueryController {

    @Autowired
    private ExpenseService service;
    @Autowired
    private ExpenseAnalysisService analysisService;

    @RequestMapping(value= "/yards/{yardId}/expenses", method= GET)
    @ResponseBody public List<ExpenseDto> getAll(@PathVariable("yardId") long yardId) {
        return service.listExpensesForYard(yardId);
    }

    @RequestMapping(value= "/yards/{yardId}/expenses/{id}", method= GET)
    @ResponseBody public ExpenseDto getExpense(@PathVariable("yardId") long yardId, @PathVariable("id") long id) {
        return service.findByIdAndYardId(id, yardId);
    }

    @RequestMapping(value= "/yards/expenses/deadlines", method= GET)
    @ResponseBody public List<DeadlinesDto> getDeadlines() {
        return analysisService.listMonthlyDeadlines();
    }

    @RequestMapping(value= "/yards/expenses/allPaymentStatuses", method= GET)
    @ResponseBody public List<PaymentStateDto> getListPaymentStatuses() {
        List<PaymentStateDto> states = new ArrayList<>();
        for (PaymentState state : PaymentState.values()) {
            states.add(new PaymentStateDto(state.toString(), state.getName()));
        }
        return states;
    }

    @RequestMapping(value= "/yards/expenses/allExpenseCategories", method= GET)
    @ResponseBody public List<ExpenseCategoryDto> getListExpenseCategories() {
        List<ExpenseCategoryDto> categories = new ArrayList<>();
        for(ExpenseCategory category : ExpenseCategory.values()) {
            categories.add(new ExpenseCategoryDto(category.toString(), category.getName()));
        }
        return categories;
    }
}