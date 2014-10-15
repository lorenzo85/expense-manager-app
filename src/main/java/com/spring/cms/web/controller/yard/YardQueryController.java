package com.spring.cms.web.controller.yard;

import com.spring.cms.service.dto.ExtendedYardDto;
import com.spring.cms.service.dto.YardDto;
import com.spring.cms.service.yard.YardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class YardQueryController {

    @Autowired
    private YardService service;

    @RequestMapping(value="/yards", method= GET)
    public @ResponseBody List<YardDto> getAll() {
        return service.findAll();
    }

    @RequestMapping(value= "/yards/{id}", method= GET)
    public @ResponseBody YardDto getYard(@PathVariable("id") long id) {
        return service.findOne(id);
    }

    @RequestMapping(value= "/yards/{id}/details", method= GET)
    public @ResponseBody ExtendedYardDto getYardDetails(@PathVariable("id") long id) {
        return service.getYardDetails(id);
    }
}