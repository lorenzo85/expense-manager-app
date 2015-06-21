package org.cms.service.yard;

import lombok.Getter;
import lombok.Setter;
import org.cms.service.income.IncomeDto;
import org.cms.service.expense.ExpenseDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class YardExtendedDto extends YardDto {

    private YardSummaryDto summary;
    private List<IncomeDto> incomes = new ArrayList<IncomeDto>();
    private List<ExpenseDto> expenses = new ArrayList<ExpenseDto>();

}