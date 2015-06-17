package org.cms.data.service;

import org.cms.data.dto.IncomeDto;

import java.util.List;

public interface IncomeService extends BaseService<IncomeDto, Long> {

    void delete(long id, long yardId);

    List<IncomeDto> listIncomesForYard(long yardId);

    IncomeDto findByIdAndYardId(long id, long yardId);
}
