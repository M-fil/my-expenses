package com.company.modules.Expenses.components.ExpensesHeader;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.auth.AuthService;
import com.company.modules.Expenses.components.CreateExpenseModal.CreateExpenseModal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExpensesHeader extends ComponentEntity {
    private float expensesAmount;
    private String currency;
    private ArrayList<ExpenseCategory> expenseCategories;
    private JLabel totalExpensesLabel;
    private AuthService authService;

    public ExpensesHeader(float expensesAmount, String currency, ArrayList<ExpenseCategory> categories) {
        ExpensesHeader.Text.put("create-expense-text", "Create New Expense");
        ExpensesHeader.Text.put("logout-text", "Log out");

        this.expensesAmount = expensesAmount;
        this.currency = currency;
        this.expenseCategories = categories;
        authService = new AuthService();
    }

    public void updateTotalAmount(float totalAmount) {
        totalExpensesLabel.setText(totalAmount + currency);
    }

    @Override
    public JPanel render() {
        JPanel headerContainer = new JPanel();
        String totalExpensesText = String.format("%.2f", expensesAmount) + currency;
        totalExpensesLabel = new JLabel(totalExpensesText);

        JButton createExpensesButton = new JButton(ExpensesHeader.Text.get("create-expense-text"));
        createExpensesButton.addActionListener((event) -> {
            CreateExpenseModal modal = new CreateExpenseModal(CreateExpenseModal.DEFAULT_DIALOG_WIDTH, CreateExpenseModal.DEFAULT_DIALOG_HEIGHT);
            modal.setCategories(expenseCategories);
            modal.render();
            modal.show();
        });
        JButton logOutButton = new JButton(ExpensesHeader.Text.get("logout-text"));
        logOutButton.addActionListener((event) -> {
            authService.signOut();
        });

        headerContainer.add(totalExpensesLabel, BorderLayout.WEST);
        headerContainer.add(createExpensesButton, BorderLayout.EAST);
        headerContainer.add(logOutButton);

        return  headerContainer;
    }
}
