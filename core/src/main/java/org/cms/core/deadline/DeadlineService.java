package org.cms.core.deadline;

import java.util.List;

public interface DeadlineService {
    List<DeadlinesExpenseDto> listDeadlinesGroupedByYearAndMonth();
}
