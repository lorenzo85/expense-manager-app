package com.spring.cms.service.expense;

import com.spring.cms.service.dto.DeadlinesDto;

import java.util.List;

public interface ExpenseAnalysisService {

    List<DeadlinesDto> listMonthlyDeadlines();

}
