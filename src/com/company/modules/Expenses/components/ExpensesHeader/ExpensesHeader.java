package com.company.modules.Expenses.components.ExpensesHeader;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.services.expenses.ExpensesService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class ExpensesHeader extends ComponentEntity {
    int expensesAmount;
    String currency;
    ExpensesService expensesService;

    public ExpensesHeader(int expensesAmount, String currency) {
        this.expensesAmount = expensesAmount;
        this.currency = currency;
        expensesService = new ExpensesService();
        ExpensesHeader.Text.put("create-expense-text", "Create New Expense");
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
            HashMap<String, Integer> result = expensesService.createNewExpense(1, 50, "$", "No Description", 1);
        });

        headerContainer.add(totalExpensesLabel, BorderLayout.WEST);
        headerContainer.add(createExpensesButton, BorderLayout.EAST);

        return  headerContainer;
    }
}
