package com.company.modules.Expenses.components.CreateExpenseModal;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.Expenses;
import jdk.jfr.Category;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateExpenseModal extends CreateExpenseModal {
    private int expenseId;
    private int expenseNumber;
    private LocalDate creationDate;
    private int selectedCategoryIndex;
    private ArrayList<ExpenseCategory> categories;
    private String description;

    public UpdateExpenseModal(int width, int height) {
        super(width, height);

        UpdateExpenseModal.Text.put("update-expense-button-text", "Update Expense");
        categories = new ExpensesService().getAllCategories();
        setCategories(categories);
    }

    public UpdateExpenseModal setExpenseValues(
            int expenseId, int expenseNumber, LocalDate creationDate, int categoryId, String description
    ) {
        this.expenseId = expenseId;
        this.expenseNumber = expenseNumber;
        this.creationDate = creationDate;
        ExpenseCategory filteredCategories = categories.stream().filter((item) -> item.id == categoryId).findFirst().get();
        this.selectedCategoryIndex = categories.indexOf(filteredCategories);
        this.description = description;

        return this;
    }

    private void updateExpense(
            String expenseText, String description, int selectedCategoryIndex, LocalDate date
    ) {
        int expenseResult = Integer.parseInt(expenseText);
        int categoryNumber = categories.get(selectedCategoryIndex).id;
        String descriptionResult = description == "" ? CreateExpenseModal.Text.get("no-description") : description;
        System.out.println("expenseId: " + expenseId);
        HashMap<String, Integer> result = expensesService.updateExpense(
                expenseId, 1, expenseResult, "$", descriptionResult, categoryNumber, date
        );
    }

    @Override
    public JPanel render() {
        JPanel container = super.render();
        expenseInput.setTextValue(String.valueOf(expenseNumber));
        createExpenseButton.setText(UpdateExpenseModal.Text.get("update-expense-button-text"));
        datePicker.setDate(creationDate);
        selectCategoryBox.setSelectedIndex(selectedCategoryIndex);
        descriptionInput.setTextValue(description);
        dialog.setTitle(UpdateExpenseModal.Text.get("update-expense-button-text"));
        ComponentEntity.removeButtonAllActionListeners(createExpenseButton);
        createExpenseButton.addActionListener((event) -> {
            updateExpense(
                    expenseInput.getTextValue(),
                    descriptionInput.getTextValue(),
                    selectCategoryBox.getSelectedIndex(),
                    datePicker.getDate()
            );
            Expenses.getInstance().rerenderExpenses();
        });

        return container;
    }
}
