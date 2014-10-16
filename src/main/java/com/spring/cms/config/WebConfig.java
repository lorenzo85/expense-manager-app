package com.spring.cms.config;

import com.spring.cms.service.dto.ExpenseDto;
import com.spring.cms.service.dto.IncomeDto;
import com.spring.cms.web.controller.reports.ExcelView;
import com.spring.cms.web.controller.reports.styles.HeaderStyleFactory;
import com.spring.cms.web.controller.reports.styles.RowStyleFactory;
import com.spring.cms.web.controller.reports.styles.StyleFactory;
import com.spring.cms.web.controller.reports.templates.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.spring.cms.web.controller"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean(name= "excelBuilder")
    @Scope("prototype")
    public View excelBuilder(ExcelTemplate template) {
        return new ExcelView(template);
    }

    @Bean(name= "headerStyleFactory")
    @Scope("prototype")
    public StyleFactory headerStyleFactory(HSSFWorkbook workbook) {
        return new HeaderStyleFactory(workbook);
    }

    @Bean(name= "rowStyleFactory")
    @Scope("prototype")
    public StyleFactory rowStyleFactory(HSSFWorkbook workbook) {
        return new RowStyleFactory(workbook);
    }

    @Bean(name= "expenseTemplate")
    @Scope("prototype")
    public ExcelTemplate<ExpenseDto> expenseTemplate(List<ExpenseDto> expenses) {
        return new ExpenseTableTemplate(expenses);
    }

    @Bean(name= "incomeTemplate")
    @Scope("prototype")
    public ExcelTemplate<IncomeDto> incomeTemplate(List<IncomeDto> incomes) {
        return new IncomeTableTemplate(incomes);
    }

    @Bean(name= "cellBuilder")
    @Scope("prototype")
    public CellBuilder cellBuilder(HSSFRow row, StyleFactory styleFactory) {
        return new CellBuilderImpl(row, styleFactory);
    }
}