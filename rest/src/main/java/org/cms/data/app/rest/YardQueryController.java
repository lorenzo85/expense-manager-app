package org.cms.data.app.rest;

import org.cms.data.dto.ExtendedYardDto;
import org.cms.data.dto.YardDto;
import org.cms.data.service.YardService;
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
    @ResponseBody
    public List<YardDto> getAll() {
        return service.findAll();
    }

    @RequestMapping(value= "/yards/{id}", method= GET)
    @ResponseBody
    public YardDto getYard(@PathVariable("id") long id) {
        return service.findOne(id);
    }

    @RequestMapping(value= "/yards/{id}/details", method= GET)
    @ResponseBody
    public ExtendedYardDto getYardDetails(@PathVariable("id") long id) {
        return service.getYardDetails(id);
    }
}