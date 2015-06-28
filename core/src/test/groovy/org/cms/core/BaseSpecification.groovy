package org.cms.core

import org.cms.core.deadline.DeadlineService
import org.cms.core.expense.ExpenseDto
import org.cms.core.expense.ExpenseRepository
import org.cms.core.expense.ExpenseService
import org.cms.core.income.IncomeDto
import org.cms.core.income.IncomeRepository
import org.cms.core.income.IncomeService
import org.cms.core.user.UserRepository
import org.cms.core.user.UserService
import org.cms.core.yard.YardDto
import org.cms.core.yard.YardRepository
import org.cms.core.yard.YardService
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.text.SimpleDateFormat

@ContextConfiguration(
        loader = SpringApplicationContextLoader.class,
        classes = [ConfigurationRepository.class, ConfigurationService.class])
@TestPropertySource("classpath:test.properties")
class BaseSpecification extends Specification {

    @Autowired UserService userService
    @Autowired UserRepository userRepository
    @Autowired YardService yardService
    @Autowired YardRepository yardRepository
    @Autowired ExpenseService expenseService
    @Autowired ExpenseRepository expenseRepository
    @Autowired IncomeService incomeService
    @Autowired IncomeRepository incomeRepository
    @Autowired DeadlineService deadlineService

    def DATE_FORMAT = "dd/MM/yyyy";
    def DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    void cleanup() {
        incomeRepository.deleteAll()
        expenseRepository.deleteAll()
        yardRepository.deleteAll()
        userRepository.deleteAll()
    }

    def amountOf(anAmount) {
        Money.of(CurrencyUnit.EUR, anAmount);
    }

    def saveYard(name, description, contractTotalAmount) {
        def yard = YardDto.builder()
                .name(name)
                .description(description)
                .contractTotalAmount(contractTotalAmount)
                .build()
        yardService.save(yard)
    }

    def saveExpense(yardId, invoiceId, title, note, amount, expiresAt, emissionAt, paymentState, category) {
        def expense = ExpenseDto.builder()
                .yardId(yardId)
                .invoiceId(invoiceId)
                .title(title)
                .note(note)
                .amount(amount)
                .expiresAt(expiresAt)
                .emissionAt(emissionAt)
                .status(paymentState)
                .category(category)
                .build()
        expenseService.save(expense)
    }

    def saveIncome(yardId, invoiceId, paymentState, amount, note) {
        def income = IncomeDto.builder()
                .yardId(yardId)
                .invoiceId(invoiceId)
                .status(paymentState)
                .amount(amount)
                .note(note)
                .build()
        incomeService.save(income)
    }
}
