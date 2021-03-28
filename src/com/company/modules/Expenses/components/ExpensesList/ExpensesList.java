package com.company.modules.Expenses.components.ExpensesList;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.interfaces.Expense;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.Expenses;
import com.company.modules.Expenses.components.ExpenseItem.ExpenseItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExpensesList extends ComponentEntity {
    ExpensesService expensesService;
    ArrayList<Expense> expenses;

    public ExpensesList() {
        expensesService = new ExpensesService();
        expenses = expensesService.getAllExpenses();
    }

    @Override
    public JPanel render() {
        JPanel expensesListContainer = new JPanel();
        expensesListContainer.setLayout(new FlowLayout());
        for (Expense expense : expenses) {
            ExpenseItem expenseElement = new ExpenseItem(
                    expense.id,
                    expense.amount,
                    expense.currency,
                    expense.description
            );
            expenseElement.setCategory(expense.category);
            expensesListContainer.add(expenseElement.render());
        }

        return expensesListContainer;
    }
}
