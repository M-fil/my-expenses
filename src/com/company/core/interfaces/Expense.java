package com.company.core.interfaces;

import java.time.LocalDate;
import java.util.Date;

public class Expense {
    public int id;
    public int userId;
    public float amount;
    public String currency;
    public LocalDate date;
    public String description;
    public ExpenseCategory category;

    public Expense(float amount, String currency, String description, LocalDate date) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.date = date;
    }

    public Expense setUserId(int userIdToSet) {
        userId = userIdToSet;
        return this;
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
