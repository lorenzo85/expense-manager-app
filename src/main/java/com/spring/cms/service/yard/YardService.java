package com.spring.cms.service.yard;

import com.spring.cms.service.BaseService;
import com.spring.cms.service.dto.ExtendedYardDto;
import com.spring.cms.service.dto.YardDto;

public interface YardService extends BaseService<YardDto, Long> {

    ExtendedYardDto getYardDetails(long id);

}
