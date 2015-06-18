package org.cms.service.income;

import org.cms.service.commons.BaseService;

import java.util.List;

public interface IncomeService extends BaseService<IncomeDto, Long> {

    void delete(long id, long yardId);

    List<IncomeDto> listIncomesForYard(long yardId);

    IncomeDto findByIdAndYardId(long id, long yardId);
}
