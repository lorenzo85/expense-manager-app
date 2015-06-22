package org.cms.core.yard
import org.cms.core.BaseSpecification
import org.cms.core.expense.ExpenseDto
import org.cms.core.expense.ExpenseRepository
import org.cms.core.expense.ExpenseService
import org.cms.core.income.IncomeDto
import org.cms.core.income.IncomeRepository
import org.cms.core.income.IncomeService
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.springframework.beans.factory.annotation.Autowired

class YardBaseSpecification extends BaseSpecification {

    @Autowired YardService service
    @Autowired YardRepository repository
    @Autowired ExpenseService expenseService
    @Autowired ExpenseRepository expenseRepository
    @Autowired IncomeService incomeService
    @Autowired IncomeRepository incomeRepository

    void cleanup() {
        repository.deleteAll()
        expenseRepository.deleteAll()
    }

    def amountOf(anAmount) {
        Money.of(CurrencyUnit.EUR, anAmount);
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
