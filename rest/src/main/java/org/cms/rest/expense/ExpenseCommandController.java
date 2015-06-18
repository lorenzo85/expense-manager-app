package org.cms.rest.expense;

import org.cms.service.expense.ExpenseDto;
import org.cms.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class ExpenseCommandController {

    @Autowired
    private ExpenseService service;

    @RequestMapping(value= "/yards/{yardId}/expenses", method= POST)
    @ResponseBody
    public ExpenseDto postNew(@Valid @RequestBody ExpenseDto dto, @PathVariable("yardId") long yardId) {
        dto.setYardId(yardId);
        return service.save(dto);
    }

    @RequestMapping(value= "/yards/{yardId}/expenses", method= PUT)
    @ResponseBody
    public ExpenseDto putUpdate(@Valid @RequestBody ExpenseDto dto, @PathVariable("yardId") long yardId) {
        dto.setYardId(yardId);
        return service.update(dto);
    }

    @RequestMapping(value = "/yards/{yardId}/expenses/{id}", method = DELETE)
    @ResponseBody
    public void delete(@PathVariable("yardId") long yardId, @PathVariable("id") long id) {
        service.delete(id, yardId);
    }

    @RequestMapping(value = "/yards/{yardId}/expenses/{id}/markAsPaid", method= PUT)
    @ResponseBody
    public void putMarkAsPaid(@PathVariable("yardId") long yardId, @PathVariable("id") long id) {
        service.markAsPaid(id, yardId);
    }
}