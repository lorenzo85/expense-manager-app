package org.cms.data.service;

import org.cms.data.dto.ExtendedYardDto;
import org.cms.data.dto.YardDto;

public interface YardService extends BaseService<YardDto, Long> {

    ExtendedYardDto getYardDetails(long id);

}
