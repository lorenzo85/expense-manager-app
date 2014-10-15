package com.spring.cms.web.controller.yard;

import com.spring.cms.service.dto.YardDto;
import com.spring.cms.service.yard.YardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@Controller
public class YardCommandController {

    @Autowired
    private YardService service;

    @RequestMapping(value= "/yards", method= POST)
    public @ResponseBody YardDto postNew(@Valid @RequestBody YardDto dto) {
        return service.save(dto);
    }

    @RequestMapping(value= "/yards", method= PUT)
    public @ResponseBody YardDto putUpdate(@Valid @RequestBody YardDto dto) {
        return service.update(dto);
    }

    @RequestMapping(value= "/yards/{id}", method= DELETE)
    public @ResponseBody void delete(@PathVariable("id") long id) {
        service.delete(id);
    }
}