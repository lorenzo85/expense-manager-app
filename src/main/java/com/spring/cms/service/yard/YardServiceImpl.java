package com.spring.cms.service.yard;

import com.spring.cms.persistence.domain.Expense;
import com.spring.cms.persistence.domain.Income;
import com.spring.cms.persistence.domain.Yard;
import com.spring.cms.persistence.repository.YardRepository;
import com.spring.cms.service.AbstractService;
import com.spring.cms.service.aspects.IsValid;
import com.spring.cms.service.dto.ExtendedYardDto;
import com.spring.cms.service.dto.YardDto;
import com.spring.cms.service.dto.YardSummaryDto;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.spring.cms.service.yard.YardSummaryBuilder.sum;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED, readOnly = true)
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

        Money paidIncomes = sum().paid().on(incomes).compute(currency);
        Money paidExpenses = sum().paid().on(expenses).compute(currency);
        Money unPaidExpenses = sum().unpaid().on(expenses).compute(currency);

        Money deltaPaid = paidIncomes.minus(paidExpenses);

        Money contractTotalAmount = yard.getContractTotalAmount();
        Money deltaMissingIncome = contractTotalAmount.minus(paidIncomes);

        return new YardSummaryDto(paidIncomes, paidExpenses, unPaidExpenses, deltaPaid, deltaMissingIncome);
    }
}