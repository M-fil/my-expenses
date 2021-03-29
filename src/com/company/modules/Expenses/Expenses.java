package com.company.modules.Expenses;

import com.company.core.components.PageEntity.PageEntity;
import com.company.core.interfaces.Expense;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.auth.AuthService;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.components.ExpensesHeader.ExpensesHeader;
import com.company.modules.Expenses.components.ExpensesList.ExpensesList;
import com.company.modules.Expenses.components.FiltersBlock.FiltersBlock;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Expenses extends PageEntity {
    static private int DEFAULT_MAIN_COMPONENTS_PADDING = 20;

    private ExpensesService expensesService;

    private ArrayList<Expense> expenses;
    private ArrayList<ExpenseCategory> categoriesList;

    private ExpensesHeader expensesHeader;
    private FiltersBlock filtersBlock;
    private ExpensesList expensesList;

    public Expenses() {
        super();
        expensesService = new ExpensesService();
        expenses = expensesService.getAllExpenses();
        categoriesList = expensesService.getAllCategories();

        expensesHeader = new ExpensesHeader(0, "$", categoriesList);
        filtersBlock = new FiltersBlock();
        expensesList = new ExpensesList(expenses);
    }

    @Override
    public JPanel render() {
        pageWrapper = new JPanel();

        expensesService.getAllExpenses();

        JPanel expensesHeaderElement = expensesHeader.render();
        PageEntity.setTheSamePaddingForAllSides(DEFAULT_MAIN_COMPONENTS_PADDING, expensesHeaderElement);

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
