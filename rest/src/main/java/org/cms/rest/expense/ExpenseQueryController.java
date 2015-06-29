package org.cms.rest.expense;

import org.cms.core.deadline.DeadlineService;
import org.cms.core.expense.PaymentCategory;
import org.cms.core.deadline.DeadlinesExpenseDto;
import org.cms.core.expense.ExpenseCategoryDto;
import org.cms.core.expense.ExpenseDto;
import org.cms.core.expense.ExpenseService;
import org.cms.core.commons.PaymentState;
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
    private DeadlineService deadlineService;

    @RequestMapping(value= "/yards/{yardId}/expenses", method= GET)
    @ResponseBody
    public List<ExpenseDto> getAll(@PathVariable("yardId") long yardId) {
        return service.listExpensesForYard(yardId);
    }

    @RequestMapping(value= "/yards/{yardId}/expenses/{id}", method= GET)
    @ResponseBody
    public ExpenseDto getExpense(@PathVariable("yardId") long yardId, @PathVariable("id") long id) {
        return service.findByIdAndYardId(id, yardId);
    }

    @RequestMapping(value= "/yards/expenses/deadlines", method= GET)
    @ResponseBody
    public List<DeadlinesExpenseDto> getDeadlines() {
        return deadlineService.listDeadlinesExpensesGroupedByCategory();
    }

    @RequestMapping(value= "/yards/expenses/allPaymentStatuses", method= GET)
    @ResponseBody
    public List<PaymentStateDto> getListPaymentStatuses() {
        List<PaymentStateDto> states = new ArrayList<>();
        for (PaymentState state : PaymentState.values()) {
            states.add(new PaymentStateDto(state.toString(), state.getName()));
        }
        return states;
    }

    @RequestMapping(value= "/yards/expenses/allExpenseCategories", method= GET)
    @ResponseBody
    public List<ExpenseCategoryDto> getListExpenseCategories() {
        List<ExpenseCategoryDto> categories = new ArrayList<>();
        for(PaymentCategory category : PaymentCategory.values()) {
            categories.add(new ExpenseCategoryDto(category.toString(), category.getName()));
        }
        return categories;
    }

    class PaymentStateDto {

        private String value;
        private String name;

        public PaymentStateDto(String value, String name) {
            this.name = name;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}