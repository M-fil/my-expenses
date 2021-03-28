package com.company.modules.Expenses;

import com.company.core.components.PageEntity.PageEntity;
import com.company.core.services.auth.AuthService;
import com.company.modules.Expenses.components.ExpensesHeader.ExpensesHeader;
import com.company.modules.Expenses.components.FiltersBlock.FiltersBlock;

import javax.swing.*;
import java.awt.*;

public class Expenses extends PageEntity {
    private AuthService authService;
    private ExpensesHeader expensesHeader;
    private FiltersBlock filtersBlock;
    static private int DEFAULT_MAIN_COMPONENTS_PADDING = 20;

    public Expenses() {
        super();
        authService = new AuthService();
        expensesHeader = new ExpensesHeader(0, "$");
        filtersBlock = new FiltersBlock();
    }

    @Override
    public JPanel render() {
        pageWrapper = new JPanel();

        JPanel expensesHeaderElement = expensesHeader.render();
        setTheSamePaddingForAllSides(DEFAULT_MAIN_COMPONENTS_PADDING, expensesHeaderElement);

        JPanel filtersBlockElement = filtersBlock.render();
        filtersBlockElement.setBackground(Color.YELLOW);

        pageWrapper.add(expensesHeaderElement);
        pageWrapper.add(filtersBlockElement);
        pageWrapper.add(expensesHeaderElement);
        pageWrapper.add(filtersBlockElement);

        return pageWrapper;
    }
}
