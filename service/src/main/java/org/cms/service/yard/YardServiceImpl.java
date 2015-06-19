package org.cms.service.yard;

import org.cms.service.commons.BaseAbstractService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        YardExtendedDto extendedYardDto = mapper.map(yard, YardExtendedDto.class);
        YardSummaryDto summaryDto = generateYardSummary(yard);
        extendedYardDto.setSummary(summaryDto);
        return extendedYardDto;
    }

    @Override
    protected JpaRepository<Yard, Long> getRepository() {
        return repo;
    }

    private YardSummaryDto generateYardSummary(Yard yard) {
        Money paidIncomes = yard.sumOfPaidIncomes(currency);
        Money paidExpenses = yard.sumOfPaidExpenses(currency);
        Money unPaidExpenses = yard.sumOfUnPaidExpenses(currency);
        Money deltaPaid = paidIncomes.minus(paidExpenses);
        Money deltaMissingIncome = yard.contractTotalAmount.minus(paidIncomes);
        return new YardSummaryDto(paidIncomes, paidExpenses, unPaidExpenses, deltaPaid, deltaMissingIncome);
    }
}