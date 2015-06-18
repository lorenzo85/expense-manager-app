package org.cms.service.income;


import org.cms.service.commons.BaseAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class IncomeServiceImpl extends BaseAbstractService<IncomeDto, Income, Long> implements IncomeService {

    @Autowired
    private IncomeRepository repo;

    @Override
    protected JpaRepository<Income, Long> getRepository() {
        return repo;
    }

    @Override
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