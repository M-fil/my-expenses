package com.company.core.interfaces;

import java.time.LocalDate;
import java.util.Date;

public class Expense {
    public int id;
    public int userId;
    public int amount;
    public String currency;
    public LocalDate date;
    public String description;
    public ExpenseCategory category;

    public Expense(int amount, String currency, String description) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        date = LocalDate.now();
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
