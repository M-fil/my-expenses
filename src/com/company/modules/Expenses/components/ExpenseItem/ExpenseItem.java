package com.company.modules.Expenses.components.ExpenseItem;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.interfaces.Expense;
import com.company.core.interfaces.ExpenseCategory;

import javax.swing.*;

public class ExpenseItem extends ComponentEntity {
    String description;
    int amount;
    String currency;
    int id;
    ExpenseCategory category;

    public ExpenseItem(int id, int amount, String currency, String description) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    public ExpenseItem setCategory(ExpenseCategory category) {
        this.category = category;
        return this;
    }

    @Override
    public JPanel render() {
        JPanel expenseContainer = new JPanel();
        JLabel label = new JLabel(amount + currency + "/" + description + "/" + category.name);
        expenseContainer.add(label);

        return expenseContainer;
    }
}
