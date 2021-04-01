package com.company.modules.Expenses.components.ExpensesHeader;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.Expenses;
import com.company.modules.Expenses.components.CreateExpenseModal.CreateExpenseModal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExpensesHeader extends ComponentEntity {
    int expensesAmount;
    String currency;
    ExpensesService expensesService;
    ArrayList<ExpenseCategory> expenseCategories;

    public ExpensesHeader(int expensesAmount, String currency, ArrayList<ExpenseCategory> categories) {
        ExpensesHeader.Text.put("create-expense-text", "Create New Expense");

        this.expensesAmount = expensesAmount;
        this.currency = currency;
        this.expenseCategories = categories;
        expensesService = new ExpensesService();
    }

    @Override
    public JPanel render() {
        JPanel headerContainer = new JPanel();
        headerContainer.setBackground(Color.RED);
        headerContainer.setLayout(new BorderLayout());

        String totalExpensesText = expensesAmount + currency;
        JLabel totalExpensesLabel = new JLabel(totalExpensesText);
        totalExpensesLabel.setForeground(Color.WHITE);

        JButton createExpensesButton = new JButton(ExpensesHeader.Text.get("create-expense-text"));
        createExpensesButton.addActionListener((event) -> {
            CreateExpenseModal modal = new CreateExpenseModal(CreateExpenseModal.DEFAULT_DIALOG_WIDTH, CreateExpenseModal.DEFAULT_DIALOG_HEIGHT);
            modal.setCategories(expenseCategories);
            modal.render();
            modal.show();
        });

        headerContainer.add(totalExpensesLabel, BorderLayout.WEST);
        headerContainer.add(createExpensesButton, BorderLayout.EAST);

        return  headerContainer;
    }
}
