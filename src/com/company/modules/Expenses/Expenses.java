package com.company.modules.Expenses;

import com.company.core.components.PageEntity.PageEntity;
import com.company.core.interfaces.Expense;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.components.ExpensesHeader.ExpensesHeader;
import com.company.modules.Expenses.components.ExpensesList.ExpensesList;

import javax.swing.*;
import java.util.ArrayList;

public class Expenses extends PageEntity {
    static private int DEFAULT_MAIN_COMPONENTS_PADDING = 20;
    static private Expenses instance = null;
    static private int AuthedUserId;

    private ExpensesService expensesService;

    private ArrayList<Expense> expenses;
    private ArrayList<ExpenseCategory> categoriesList;
    private JPanel expensesListElement;

    private ExpensesHeader expensesHeader;

    public Expenses(int authedUserId) {
        super();
        expensesService = new ExpensesService();
        categoriesList = expensesService.getAllCategories();

        expensesHeader = new ExpensesHeader(0, "$", categoriesList);
        AuthedUserId = authedUserId;

        Expenses.instance = this;
    }

    public static int getAuthedUserId() {
        return AuthedUserId;
    }

    public static Expenses getInstance() {
        if (Expenses.instance == null) {
            Expenses.instance = new Expenses(AuthedUserId);
        }

        return Expenses.instance;
    }

    private void updateTotalAmount() {
        if (expenses != null) {
            float totalAmount = ExpensesService.calculateTotalExpenses(expenses);
            expensesHeader.updateTotalAmount(totalAmount);
        }
    }

    public void rerenderExpenses() {
        pageWrapper.remove(expensesListElement);
        pageWrapper.repaint();
        pageWrapper.revalidate();

        expenses = expensesService.getAllExpenses();
        categoriesList = expensesService.getAllCategories();
        expensesListElement = new ExpensesList(expenses).render();
        expensesListElement.setLayout(new BoxLayout(expensesListElement, BoxLayout.Y_AXIS));
        pageWrapper.add(expensesListElement);
        updateTotalAmount();

        pageWrapper.repaint();
        pageWrapper.revalidate();
    }

    @Override
    public JPanel render() {
        pageWrapper = new JPanel();

        expenses = expensesService.getAllExpenses();
        JPanel expensesHeaderElement = expensesHeader.render();
        PageEntity.setTheSamePaddingForAllSides(DEFAULT_MAIN_COMPONENTS_PADDING, expensesHeaderElement);
        updateTotalAmount();

        expensesListElement = new ExpensesList(expenses).render();
        expensesListElement.setLayout(new BoxLayout(expensesListElement, BoxLayout.Y_AXIS));

        pageWrapper.add(expensesHeaderElement);
        pageWrapper.add(expensesListElement);

        return pageWrapper;
    }
}
