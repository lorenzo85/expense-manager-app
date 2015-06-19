package org.cms.service.yard;

import org.cms.service.expense.Expense;
import org.cms.service.income.Income;
import org.cms.service.commons.BaseAbstractService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class YardServiceImpl extends BaseAbstractService<YardDto, Yard, Long> implements YardService {

    @Autowired
    private YardRepository repo;
    @Autowired
    private CurrencyUnit currency;

    @Override
    public YardExtendedDto getYardDetails(long id) {
        Yard yard = findOneOrThrow(id);

        YardExtendedDto dto = mapper.map(yard, YardExtendedDto.class);

        YardSummaryDto summaryDto = generateYardSummary(yard);
        dto.setSummary(summaryDto);

        return dto;
    }

    @Override
    protected JpaRepository<Yard, Long> getRepository() {
        return repo;
    }

    private YardSummaryDto generateYardSummary(Yard yard) {
        Collection<Income> incomes = yard.incomes;
        Collection<Expense> expenses = yard.expenses;

        Money paidIncomes = YardSummaryBuilder.sum().paid().on(incomes).compute(currency);
        Money paidExpenses = YardSummaryBuilder.sum().paid().on(expenses).compute(currency);
        Money unPaidExpenses = YardSummaryBuilder.sum().unpaid().on(expenses).compute(currency);

        Money deltaPaid = paidIncomes.minus(paidExpenses);

        Money contractTotalAmount = yard.contractTotalAmount;
        Money deltaMissingIncome = contractTotalAmount.minus(paidIncomes);

        return new YardSummaryDto(paidIncomes, paidExpenses, unPaidExpenses, deltaPaid, deltaMissingIncome);
    }
}