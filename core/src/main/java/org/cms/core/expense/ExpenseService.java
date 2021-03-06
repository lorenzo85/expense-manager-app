package org.cms.core.expense;

import org.cms.core.commons.BaseService;

import java.util.List;

public interface ExpenseService extends BaseService<ExpenseDto, Long> {

    ExpenseDto markAsPaid(long id, long yardId);

    void delete(long id, long yardId);

    List<ExpenseDto> listExpensesForYard(long yardId);

    ExpenseDto findByIdAndYardId(long id, long yardId);

}
