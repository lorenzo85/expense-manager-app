package com.spring.cms.service.expense;

import com.spring.cms.persistence.domain.Expense;
import com.spring.cms.persistence.repository.ExpenseRepository;
import com.spring.cms.service.AbstractService;
import com.spring.cms.service.aspects.IsValid;
import com.spring.cms.service.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.spring.cms.persistence.domain.PaymentState.PAID;
import static com.spring.cms.persistence.domain.PaymentState.UNPAID;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED, readOnly = false)
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
    protected CrudRepository<Expense, Long> getRepository() {
        return repo;
    }
}