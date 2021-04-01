package com.company.modules.Expenses.components.ExpensesList;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.interfaces.Expense;
import com.company.modules.Expenses.components.ExpenseItem.ExpenseItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExpensesList extends ComponentEntity {
    private ArrayList<Expense> expenses;

    public ExpensesList(ArrayList<Expense> expenses) {
        this.expenses = expenses;
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
                    expense.description,
                    expense.date
            );
            expenseElement.setCategory(expense.category);
            expensesListContainer.add(expenseElement.render());
        }

        return expensesListContainer;
    }
}
