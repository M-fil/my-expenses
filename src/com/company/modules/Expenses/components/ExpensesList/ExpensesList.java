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
        ExpensesList.Text.put("no-expenses-text", "There are no any expense now. Click 'Create expense' to create one");
        this.expenses = expenses;
    }

    @Override
    public JPanel render() {
        JPanel expensesListContainer = new JPanel();
        expensesListContainer.setLayout(new BoxLayout(expensesListContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(expensesListContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(400, 300));

        JPanel noElementsPanel = new JPanel(new BorderLayout());
        JLabel noElementsLabel = new JLabel(ExpensesList.Text.get("no-expenses-text"));
        noElementsPanel.add(noElementsLabel);

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

        if (expenses.size() == 0) {
            contentPane.add(noElementsPanel);
        } else {
            contentPane.add(scrollPane);
        }

        return contentPane;
    }
}
