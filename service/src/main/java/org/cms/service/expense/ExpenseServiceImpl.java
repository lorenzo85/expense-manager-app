package org.cms.service.expense;

import org.apache.commons.lang3.tuple.Pair;
import org.cms.service.commons.BaseAbstractService;
import org.cms.service.commons.PaymentAggregator;
import org.joda.money.CurrencyUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toList;
import static org.cms.service.commons.PaymentState.PAID;
import static org.cms.service.commons.PaymentState.UNPAID;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ExpenseServiceImpl extends BaseAbstractService<ExpenseDto, Expense, Long> implements ExpenseService {

    @Autowired
    private ExpenseRepository repo;
    @Autowired
    private CurrencyUnit currencyUnit;

    @Override
    public ExpenseDto markAsPaid(long id, long yardId) {
        ExpenseDto expense = findOne(id);

        checkArgument(expense.getYardId() == yardId);
        checkState(expense.getStatus() == UNPAID);

        expense.setStatus(PAID);
        return update(expense);
    }

    @Override
    public void delete(long id, long yardId) {
        ExpenseDto expense = findOne(id);
        checkArgument(expense.getYardId() == yardId);
        delete(id);
    }

    @Override
    public List<ExpenseDto> listExpensesForYard(long yardId) {
        Iterable<Expense> expenses = repo.listByYard(yardId);
        return mapAll(expenses);
    }

    @Override
    public ExpenseDto findByIdAndYardId(long id, long yardId) {
        Expense expense = repo.findByIdAndYardId(id, yardId);
        return mapper.map(expense, ExpenseDto.class);
    }

    @Override
    public List<DeadlinesDto> listDeadlinesGroupedByYearAndMonth() {
        List<Expense> unpaidExpenses = repo.listByPaymentStateOrderedByYearAndMonthAndCategory(UNPAID);
        Map<Pair<String, String>, List<Expense>> expensesGroupedByYearAndMonth =
                PaymentAggregator.groupByYearAndMonth(unpaidExpenses);

        // Construct new ExpensesGroupByYearAndMonth which takes care of computing
        // the total sum and the partial sum for each expense category.
        List<ExpensesGroupByYearAndMonth> expensesGroupsList = expensesGroupedByYearAndMonth
                .entrySet()
                .stream()
                .map(expensesForYearAndMonth -> {
                    Pair<String, String> key = expensesForYearAndMonth.getKey();
                    return ExpensesGroupByYearAndMonth.builder(currencyUnit)
                            .year(key.getLeft())
                            .month(key.getRight())
                            .expenses(expensesForYearAndMonth.getValue())
                            .build();
                }).collect(toList());

        return expensesGroupsList.stream()
                .map(expenseGroup -> mapper.map(expenseGroup, DeadlinesDto.class))
                .collect(toList());
    }

    @Override
    protected JpaRepository<Expense, Long> getRepository() {
        return repo;
    }
}