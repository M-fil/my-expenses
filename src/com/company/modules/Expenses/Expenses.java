package com.company.modules.Expenses;

import com.company.core.components.PageEntity.PageEntity;
import com.company.core.services.auth.AuthService;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.components.ExpensesHeader.ExpensesHeader;
import com.company.modules.Expenses.components.ExpensesList.ExpensesList;
import com.company.modules.Expenses.components.FiltersBlock.FiltersBlock;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Expenses extends PageEntity {
    private AuthService authService;
    private ExpensesService expensesService;

    private ExpensesHeader expensesHeader;
    private FiltersBlock filtersBlock;
    private ExpensesList expensesList;
    static private int DEFAULT_MAIN_COMPONENTS_PADDING = 20;

    public Expenses() {
        super();
        authService = new AuthService();
        expensesService = new ExpensesService();

        expensesHeader = new ExpensesHeader(0, "$");
        filtersBlock = new FiltersBlock();
        expensesList = new ExpensesList();
    }

    @Override
    public JPanel render() {
        pageWrapper = new JPanel();

        expensesService.getAllExpenses();

        JPanel expensesHeaderElement = expensesHeader.render();
        setTheSamePaddingForAllSides(DEFAULT_MAIN_COMPONENTS_PADDING, expensesHeaderElement);

        JPanel filtersBlockElement = filtersBlock.render();
        filtersBlockElement.setBackground(Color.YELLOW);

        JPanel expensesListElement = expensesList.render();
        expensesListElement.setBackground(Color.GREEN);
        expensesListElement.setLayout(new BoxLayout(expensesListElement, BoxLayout.Y_AXIS));

        pageWrapper.add(expensesHeaderElement);
        pageWrapper.add(filtersBlockElement);
        pageWrapper.add(expensesListElement);

        return pageWrapper;
    }
}
