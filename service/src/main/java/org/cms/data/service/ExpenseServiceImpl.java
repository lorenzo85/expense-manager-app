package org.cms.data.service;

import com.google.common.base.Preconditions;
import org.cms.data.dto.DeadlinesDto;
import org.cms.data.dto.ExpenseDto;
import org.cms.data.domain.Expense;
import org.cms.data.domain.PaymentState;
import org.cms.data.repository.ExpenseRepository;
import org.cms.data.utilities.IsValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ExpenseServiceImpl extends AbstractService<ExpenseDto, Expense, Long> implements ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    @Override
    public ExpenseDto save(@IsValid ExpenseDto dto) {
        throwIfFound(dto.getId());

        Expense expense = mapper.map(dto, Expense.class);
        expense = repo.save(expense);

        return mapper.map(expense, ExpenseDto.class);
    }

    @Override
    public ExpenseDto update(@IsValid ExpenseDto dto) {
        findOneOrThrow(dto.getId());

        Expense expense = mapper.map(dto, Expense.class);
        expense = repo.save(expense);

        return mapper.map(expense, ExpenseDto.class);
    }

    @Override
    public ExpenseDto markAsPaid(long id, long yardId) {
        ExpenseDto expense = findOne(id);

        Preconditions.checkArgument(expense.getYardId() == yardId);
        Preconditions.checkState(expense.getStatus() == PaymentState.UNPAID);

        expense.setStatus(PaymentState.PAID);
        return update(expense);
    }

    @Override
    public void delete(long id, long yardId) {
        ExpenseDto expense = findOne(id);
        Preconditions.checkArgument(expense.getYardId() == yardId);

        delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseDto> listExpensesForYard(long yardId) {
        Iterable<Expense> expenses = repo.listByYard(yardId);
        return mapAll(expenses);
    }

    @Override
    @Transactional(readOnly = true)
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

        return DeadlinesDto.computeSumAndSumForCategories(expenses);
    }

    @Override
    protected CrudRepository<Expense, Long> getRepository() {
        return repo;
    }
}