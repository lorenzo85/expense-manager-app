package com.spring.cms.service.expense;

import com.spring.cms.persistence.domain.Expense;
import com.spring.cms.persistence.repository.ExpenseRepository;
import com.spring.cms.service.AbstractService;
import com.spring.cms.service.aspects.IsValid;
import com.spring.cms.service.dto.DeadlinesDto;
import com.spring.cms.service.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.spring.cms.persistence.domain.PaymentState.PAID;
import static com.spring.cms.persistence.domain.PaymentState.UNPAID;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Locale.ENGLISH;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED, readOnly = true)
public class ExpenseServiceImpl extends AbstractService<ExpenseDto, Expense, Long> implements ExpenseService {

    private static final SimpleDateFormat MONTH_FORMATTER = new SimpleDateFormat("MMMM", ENGLISH);
    private static final SimpleDateFormat YEAR_FORMATTER = new SimpleDateFormat("yyyy", ENGLISH);

    @Autowired
    private ExpenseRepository repo;

    @Override
    @Transactional(readOnly = false)
    public ExpenseDto save(ExpenseDto dto) {
        throwIfFound(dto.getId());

        Expense expense = mapper.map(dto, Expense.class);
        expense = repo.save(expense);

        return mapper.map(expense, ExpenseDto.class);
    }

    @Override
    @Transactional(readOnly = false)
    public ExpenseDto update(@IsValid ExpenseDto dto) {
        findOneOrThrow(dto.getId());

        Expense expense = mapper.map(dto, Expense.class);
        expense = repo.save(expense);

        return mapper.map(expense, ExpenseDto.class);
    }

    @Override
    @Transactional(readOnly = false)
    public ExpenseDto markAsPaid(long id, long yardId) {
        ExpenseDto expense = findOne(id);

        checkArgument(expense.getYardId() == yardId);
        checkState(expense.getStatus() == UNPAID);

        expense.setStatus(PAID);
        return update(expense);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id, long yardId) {
        ExpenseDto expense = findOne(id);
        checkArgument(expense.getYardId() == yardId);

        delete(id);
    }

    @Override
    public List<DeadlinesDto> listMonthlyDeadlines() {
        Map<String, DeadlinesDto> deadlines = new LinkedHashMap<>();

        repo.listUnpaidExpensesOrderedByYearAndMonthAndCategory().forEach((e) -> {
            processExpense(e, deadlines);
        });

        return new ArrayList<>(deadlines.values());
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
    protected CrudRepository<Expense, Long> getRepository() {
        return repo;
    }

    private void processExpense(Expense expense, Map<String, DeadlinesDto> deadlines) {
        String month = MONTH_FORMATTER.format(expense.getExpiresAt());
        String year = YEAR_FORMATTER.format(expense.getExpiresAt());
        String key = month + year;

        if(deadlines.get(key) == null) {
            DeadlinesDto deadline = new DeadlinesDto(year, month);
            deadlines.put(key, deadline);
        }

        ExpenseDto mapped = mapper.map(expense, ExpenseDto.class);
        deadlines.get(key).addExpense(mapped);
    }
}