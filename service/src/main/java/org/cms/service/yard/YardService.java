package org.cms.service.yard;

import org.cms.service.commons.BaseService;

public interface YardService extends BaseService<YardDto, Long> {

    YardExtendedDto getYardDetails(long id);

}
