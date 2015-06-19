package org.cms.service.expense;

import org.cms.service.commons.PaymentState;
import org.cms.service.commons.BaseAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ExpenseServiceImpl extends BaseAbstractService<ExpenseDto, Expense, Long> implements ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    @Override
    public ExpenseDto markAsPaid(long id, long yardId) {
        ExpenseDto expense = findOne(id);

        checkArgument(expense.getYardId() == yardId);
        checkState(expense.getStatus() == PaymentState.UNPAID);

        expense.setStatus(PaymentState.PAID);
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
    public List<DeadlinesDto> listMonthlyDeadlines() {
        List<Expense> unpaidExpenses = repo.listUnpaidExpensesOrderedByYearAndMonthAndCategory();

        List<ExpenseDto> expenses = new ArrayList<>();
        unpaidExpenses.forEach(e -> {
            ExpenseDto mapped = mapper.map(e, ExpenseDto.class);
            expenses.add(mapped);
        });

        // TODO: This is totally wrong. Should be in the domain object.
        return DeadlinesDto.computeSumAndSumForCategories(expenses);
    }

    @Override
    protected JpaRepository<Expense, Long> getRepository() {
        return repo;
    }
}