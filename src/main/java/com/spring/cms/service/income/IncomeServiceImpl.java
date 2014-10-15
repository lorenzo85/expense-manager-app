package com.spring.cms.service.income;

import com.spring.cms.persistence.domain.Income;
import com.spring.cms.persistence.repository.IncomeRepository;
import com.spring.cms.service.AbstractService;
import com.spring.cms.service.aspects.IsValid;
import com.spring.cms.service.dto.IncomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED, readOnly = true)
public class IncomeServiceImpl extends AbstractService<IncomeDto, Income, Long> implements IncomeService {

    @Autowired
    private IncomeRepository repo;

    @Override
    @Transactional(readOnly = false)
    public IncomeDto save(@IsValid IncomeDto dto) {
        throwIfFound(dto.getId());

        Income income = mapper.map(dto, Income.class);
        income = repo.save(income);

        return mapper.map(income, IncomeDto.class);
    }

    @Override
    @Transactional(readOnly = false)
    public IncomeDto update(@IsValid IncomeDto dto) {
        findOneOrThrow(dto.getId());

        Income income = mapper.map(dto, Income.class);
        income = repo.save(income);

        return mapper.map(income, IncomeDto.class);
    }

    @Override
    protected CrudRepository<Income, Long> getRepository() {
        return repo;
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id, long yardId) {
        IncomeDto income = findOne(id);
        checkArgument(income.getYardId() == yardId);

        delete(id);
    }

    @Override
    public List<IncomeDto> listIncomesForYard(long yardId) {
        Iterable<Income> incomes = repo.listByYard(yardId);

        return mapAll(incomes);
    }

    @Override
    public IncomeDto findByIdAndYardId(long id, long yardId) {
        Income income = repo.findByIdAndYardId(id, yardId);

        return mapper.map(income, IncomeDto.class);
    }
}