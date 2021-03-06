package org.cms.core.yard;

import org.cms.core.commons.BaseAbstractService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED)
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
    public List<YardDto> findAll(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<Yard> thePage = repo.findAll(pageRequest);
        List<YardDto> yards = new ArrayList<>();
        thePage.forEach(yard -> yards.add(mapper.map(yard, YardDto.class)));
        return yards;
    }

    @Override
    protected JpaRepository<Yard, Long> getRepository() {
        return repo;
    }

    private YardSummaryDto generateYardSummary(Yard yard) {
        Money deltaPaid = yard.getReturn(currency);
        Money paidIncomes = yard.sumOfPaidIncomes(currency);
        Money paidExpenses = yard.sumOfPaidExpenses(currency);
        Money unPaidExpenses = yard.sumOfUnPaidExpenses(currency);
        Money deltaMissingIncome = yard.getIncomesToBePaid(currency);
        return new YardSummaryDto(paidIncomes, paidExpenses, unPaidExpenses, deltaPaid, deltaMissingIncome);
    }
}