package org.cms.rest.income;

import org.cms.core.income.IncomeDto;
import org.cms.core.income.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class IncomeCommandController {

    @Autowired
    private IncomeService service;

    @RequestMapping(value= "/yards/{yardId}/incomes", method= POST)
    @ResponseBody
    public IncomeDto postNew(@Valid @RequestBody IncomeDto dto, @PathVariable("yardId") long yardId) {
        dto.setYardId(yardId);
        return service.save(dto);
    }

    @RequestMapping(value= "/yards/{yardId}/incomes", method= PUT)
    @ResponseBody
    public IncomeDto putUpdate(@Valid @RequestBody IncomeDto dto, @PathVariable("yardId") long yardId) {
        dto.setYardId(yardId);
        return service.update(dto);
    }

    @RequestMapping(value= "/yards/{yardId}/incomes/{id}", method= DELETE)
    @ResponseBody
    public void delete(@PathVariable("yardId") long yardId, @PathVariable("id") long id) {
        service.delete(id, yardId);
    }
}