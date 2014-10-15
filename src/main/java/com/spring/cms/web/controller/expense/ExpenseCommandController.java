package com.spring.cms.web.controller.expense;

import com.spring.cms.service.dto.ExpenseDto;
import com.spring.cms.service.expense.ExpenseService;
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
    public @ResponseBody ExpenseDto postNew(@Valid @RequestBody ExpenseDto dto, @PathVariable("yardId") long yardId) {
        dto.setYardId(yardId);
        return service.save(dto);
    }

    @RequestMapping(value= "/yards/{yardId}/expenses", method= PUT)
    public @ResponseBody ExpenseDto putUpdate(@Valid @RequestBody ExpenseDto dto, @PathVariable("yardId") long yardId) {
        dto.setYardId(yardId);
        return service.update(dto);
    }

    @RequestMapping(value = "/yards/{yardId}/expenses/{id}", method = DELETE)
    public @ResponseBody void delete(@PathVariable("yardId") long yardId, @PathVariable("id") long id) {
        service.delete(id, yardId);
    }

    @RequestMapping(value = "/yards/{yardId}/expenses/{id}/markAsPaid", method= PUT)
    public @ResponseBody void putMarkAsPaid(@PathVariable("yardId") long yardId, @PathVariable("id") long id) {
        service.markAsPaid(id, yardId);
    }
}