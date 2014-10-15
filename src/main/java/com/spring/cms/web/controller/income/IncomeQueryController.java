package com.spring.cms.web.controller.income;

import com.spring.cms.service.dto.IncomeDto;
import com.spring.cms.service.income.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class IncomeQueryController {

    @Autowired
    private IncomeService service;

    @RequestMapping(value= "/yards/{yardId}/incomes", method= GET)
    public @ResponseBody List<IncomeDto> getAll(@PathVariable("yardId") long yardId) {
        return service.listIncomesForYard(yardId);
    }

    @RequestMapping(value= "/yards/{yardId}/incomes/{id}", method= GET)
    public @ResponseBody IncomeDto getIncome(@PathVariable("yardId") long yardId, @PathVariable("id") long id) {
        return service.findByIdAndYardId(id, yardId);
    }
}