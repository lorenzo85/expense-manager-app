package org.cms.core.income;


import org.cms.core.commons.BaseAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.transaction.annotation.Propagation.*;

@Service
@Transactional(propagation = REQUIRED)
public class IncomeServiceImpl extends BaseAbstractService<IncomeDto, Income, Long> implements IncomeService {

    @Autowired
    private IncomeRepository repo;

    @Override
    protected JpaRepository<Income, Long> getRepository() {
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