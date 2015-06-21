package org.cms.rest.yard;

import org.cms.service.yard.YardExtendedDto;
import org.cms.service.yard.YardDto;
import org.cms.service.yard.YardService;
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
    public YardExtendedDto getYardDetails(@PathVariable("id") long id) {
        return service.getYardDetails(id);
    }

    @RequestMapping(value="/yards/page/{page}/size/{size}", method = GET)
    @ResponseBody
    public List<YardDto> getAll(@PathVariable int page, @PathVariable int size) {
        return service.findAll(page, size);
    }
}