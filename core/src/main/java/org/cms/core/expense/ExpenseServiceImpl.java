package org.cms.core.expense;

import org.cms.core.commons.BaseAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static org.cms.core.commons.PaymentState.PAID;
import static org.cms.core.commons.PaymentState.UNPAID;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED)
public class ExpenseServiceImpl extends BaseAbstractService<ExpenseDto, Expense, Long> implements ExpenseService {

    @Autowired
    private ExpenseRepository repo;

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
    protected JpaRepository<Expense, Long> getRepository() {
        return repo;
    }
}