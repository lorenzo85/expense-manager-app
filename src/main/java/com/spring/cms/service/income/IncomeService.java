package com.spring.cms.service.income;

import com.spring.cms.service.BaseService;
import com.spring.cms.service.dto.IncomeDto;

import java.util.List;

public interface IncomeService extends BaseService<IncomeDto, Long> {

    void delete(long id, long yardId);

    List<IncomeDto> listIncomesForYard(long yardId);

    IncomeDto findByIdAndYardId(long id, long yardId);
}
