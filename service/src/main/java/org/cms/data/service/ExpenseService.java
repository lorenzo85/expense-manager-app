package org.cms.data.service;

import org.cms.data.dto.DeadlinesDto;
import org.cms.data.dto.ExpenseDto;

import java.util.List;

public interface ExpenseService extends BaseService<ExpenseDto, Long> {

    ExpenseDto markAsPaid(long id, long yardId);

    void delete(long id, long yardId);

    List<ExpenseDto> listExpensesForYard(long yardId);

    ExpenseDto findByIdAndYardId(long id, long yardId);

    List<DeadlinesDto> listMonthlyDeadlines();
}
