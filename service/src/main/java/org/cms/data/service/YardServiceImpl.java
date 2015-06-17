package org.cms.data.service;

import org.cms.data.dto.ExtendedYardDto;
import org.cms.data.dto.YardDto;
import org.cms.data.dto.YardSummaryDto;
import org.cms.data.domain.Expense;
import org.cms.data.domain.Income;
import org.cms.data.domain.Yard;
import org.cms.data.repository.YardRepository;
import org.cms.data.utilities.IsValid;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class YardServiceImpl extends AbstractService<YardDto, Yard, Long> implements YardService {

    @Autowired
    private YardRepository repo;
    @Autowired
    private CurrencyUnit currency;

    @Override
    public ExtendedYardDto getYardDetails(long id) {
        Yard yard = findOneOrThrow(id);

        ExtendedYardDto dto = mapper.map(yard, ExtendedYardDto.class);

        YardSummaryDto summaryDto = generateYardSummary(yard);
        dto.setSummary(summaryDto);

        return dto;
    }

    @Override
    @Transactional(readOnly = false)
    public YardDto save(@IsValid YardDto dto) {
        throwIfFound(dto.getId());

        Yard mapped = mapper.map(dto, Yard.class);
        Yard saved = repo.save(mapped);

        return mapper.map(saved, YardDto.class);
    }

    @Override
    @Transactional(readOnly = false)
    public YardDto update(@IsValid YardDto dto) {
        findOneOrThrow(dto.getId());

        Yard mapped = mapper.map(dto, Yard.class);
        Yard updated = repo.save(mapped);

        return mapper.map(updated, YardDto.class);
    }

    @Override
    protected CrudRepository<Yard, Long> getRepository() {
        return repo;
    }

    private YardSummaryDto generateYardSummary(Yard yard) {
        Collection<Income> incomes = yard.getIncomes();
        Collection<Expense> expenses = yard.getExpenses();

        Money paidIncomes = YardSummaryBuilder.sum().paid().on(incomes).compute(currency);
        Money paidExpenses = YardSummaryBuilder.sum().paid().on(expenses).compute(currency);
        Money unPaidExpenses = YardSummaryBuilder.sum().unpaid().on(expenses).compute(currency);

        Money deltaPaid = paidIncomes.minus(paidExpenses);

        Money contractTotalAmount = yard.getContractTotalAmount();
        Money deltaMissingIncome = contractTotalAmount.minus(paidIncomes);

        return new YardSummaryDto(paidIncomes, paidExpenses, unPaidExpenses, deltaPaid, deltaMissingIncome);
    }
}