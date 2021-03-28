package com.company.modules.Expenses.components.ExpensesHeader;

import com.company.core.components.ComponentEntity.ComponentEntity;

import javax.swing.*;
import java.awt.*;

public class ExpensesHeader extends ComponentEntity {
    int expensesAmount;
    String currency;

    public ExpensesHeader(int expensesAmount, String currency) {
        this.expensesAmount = expensesAmount;
        this.currency = currency;
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

        headerContainer.add(totalExpensesLabel, BorderLayout.WEST);
        headerContainer.add(createExpensesButton, BorderLayout.EAST);

        return  headerContainer;
    }
}
