package com.spring.cms.service.expense;

import com.spring.cms.persistence.domain.Expense;
import com.spring.cms.persistence.repository.ExpenseRepository;
import com.spring.cms.service.dto.DeadlinesDto;
import com.spring.cms.service.dto.ExpenseDto;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.spring.cms.service.dto.DeadlinesDto.*;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation= REQUIRED, readOnly= true)
public class ExpenseAnalysisServiceImpl implements ExpenseAnalysisService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private ExpenseRepository repo;

    @Override
    public List<DeadlinesDto> listMonthlyDeadlines() {
        List<Expense> unpaidExpenses = repo.listUnpaidExpensesOrderedByYearAndMonthAndCategory();

        List<ExpenseDto> expenses = new ArrayList<>();
        unpaidExpenses.forEach(e -> {
            ExpenseDto mapped = mapper.map(e, ExpenseDto.class);
            expenses.add(mapped);
        });

        return computeSumAndSumForCategories(expenses);
    }
}