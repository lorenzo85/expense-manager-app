package org.cms.data.service;

import com.google.common.base.Preconditions;
import org.cms.data.dto.IncomeDto;
import org.cms.data.domain.Income;
import org.cms.data.repository.IncomeRepository;
import org.cms.data.utilities.IsValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
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
        Preconditions.checkArgument(income.getYardId() == yardId);

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