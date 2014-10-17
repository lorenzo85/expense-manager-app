package com.spring.cms.service.expense;

import com.spring.cms.persistence.domain.Expense;
import com.spring.cms.persistence.domain.ExpenseCategory;
import com.spring.cms.persistence.repository.ExpenseRepository;
import com.spring.cms.service.dto.DeadlinesDto;
import com.spring.cms.service.dto.ExpenseDto;
import org.dozer.Mapper;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Locale.ENGLISH;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED, readOnly = true)
public class ExpenseAnalysisServiceImpl implements ExpenseAnalysisService {

    private static final SimpleDateFormat MONTH_FORMATTER = new SimpleDateFormat("MMMM", ENGLISH);
    private static final SimpleDateFormat YEAR_FORMATTER = new SimpleDateFormat("yyyy", ENGLISH);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Mapper mapper;
    @Autowired
    private ExpenseRepository repo;


    @Override
    public List<DeadlinesDto> listMonthlyDeadlines() {
        Map<String, DeadlinesDto> deadlines = new LinkedHashMap<>();

        List<Expense> unpaidExpenses = repo.listUnpaidExpensesOrderedByYearAndMonthAndCategory();
        unpaidExpenses.forEach(e -> processExpense(e, deadlines) );

        return new ArrayList<>(deadlines.values());
    }

    private void processExpense(Expense expense, Map<String, DeadlinesDto> deadlines) {
        Money expenseAmount = expense.getAmount();
        Timestamp expiresAt = expense.getExpiresAt();
        ExpenseCategory expenseCategory = expense.getCategory();

        String month = MONTH_FORMATTER.format(expiresAt);
        String year = YEAR_FORMATTER.format(expiresAt);
        String key = month + year;

        if(!deadlines.containsKey(key)) {
            deadlines.put(key, new DeadlinesDto(year, month));
        }

        DeadlinesDto deadline = deadlines.get(key);

        addExpense(deadline, expense);
        updateTotal(deadline, expenseAmount);
        updateTotalForCategory(deadline, expenseAmount, expenseCategory);
    }

    private void addExpense(DeadlinesDto deadline, Expense expense) {
        ExpenseDto mapped = mapper.map(expense, ExpenseDto.class);
        deadline.addExpense(mapped);
    }

    private void updateTotal(DeadlinesDto deadline, Money expenseAmount) {
        Money monthTotal = deadline.getTotal();

        if(monthTotal == null) {
            monthTotal = context.getBean(Money.class);
        }

        monthTotal = monthTotal.plus(expenseAmount);
        deadline.updateTotal(monthTotal);
    }

    private void updateTotalForCategory(DeadlinesDto deadline, Money expenseAmount, ExpenseCategory category) {
        Money monthCategoryTotal = deadline.getTotalForCategory(category);

        if(monthCategoryTotal == null) {
            monthCategoryTotal = context.getBean(Money.class);
        }

        monthCategoryTotal = monthCategoryTotal.plus(expenseAmount);
        deadline.updateTotalForCategory(category, monthCategoryTotal);
    }
}