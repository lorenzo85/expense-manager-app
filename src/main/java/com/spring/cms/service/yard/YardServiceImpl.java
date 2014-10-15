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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;

import static com.spring.cms.service.analysis.AnalysisStrategyBuilder.sum;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED, readOnly = true)
public class YardServiceImpl extends AbstractService<YardDto, Yard, Long> implements YardService {

    @Autowired
    private YardRepository repo;

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

        BigDecimal paidIncomes = sum().paid().on(incomes);
        BigDecimal paidExpenses = sum().paid().on(expenses);
        BigDecimal unPaidExpenses = sum().unpaid().on(expenses);

        BigDecimal deltaPaid = paidIncomes.subtract(paidExpenses);

        BigDecimal contractTotalAmount = yard.getContractTotalAmount();
        BigDecimal deltaMissingIncome = contractTotalAmount.subtract(paidIncomes);

        return new YardSummaryDto(paidIncomes, paidExpenses, unPaidExpenses, deltaPaid, deltaMissingIncome);
    }
}