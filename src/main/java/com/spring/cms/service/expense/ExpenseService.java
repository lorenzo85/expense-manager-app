package com.spring.cms.service.expense;

import com.spring.cms.service.BaseService;
import com.spring.cms.service.dto.DeadlinesDto;
import com.spring.cms.service.dto.ExpenseDto;

import java.util.List;

public interface ExpenseService extends BaseService<ExpenseDto, Long> {

    ExpenseDto markAsPaid(long id, long yardId);

    void delete(long id, long yardId);

    List<DeadlinesDto> listMonthlyDeadlines();

    List<ExpenseDto> listExpensesForYard(long yardId);

    ExpenseDto findByIdAndYardId(long id, long yardId);
}
