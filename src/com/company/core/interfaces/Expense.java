package com.company.core.interfaces;
import java.time.LocalDate;

public class Expense {
    private int id;
    private float amount;
    private String currency;
    private LocalDate date;
    private String description;
    private ExpenseCategory category;

    public Expense(float amount, String currency, String description, LocalDate date) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseCategory getExpenseCategory() {
        return category;
    }

    public Expense setExpenseId(int expenseId) {
        id = expenseId;
        return this;
    }

    public Expense setExpenseCategory(ExpenseCategory expenseCategory) {
        category = expenseCategory;
        return this;
    }
}
