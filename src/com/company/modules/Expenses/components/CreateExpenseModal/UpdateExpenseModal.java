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
    private float expenseNumber;
    private LocalDate creationDate;
    private int selectedCategoryIndex;
    private ArrayList<ExpenseCategory> categories;
    private String description;

    public UpdateExpenseModal(int width, int height) {
        super(width, height);

        UpdateExpenseModal.Text.put("update-expense-button-text", "Update Expense");
        UpdateExpenseModal.Text.put("default-error", "Error while updating an expense");
        categories = new ExpensesService().getAllCategories();
        setCategories(categories);
    }

    public UpdateExpenseModal setExpenseValues(
            int expenseId, float expenseNumber, LocalDate creationDate, int categoryId, String description
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
        String errorMessage = checkIfFieldsAreValid(expenseText, date);
        int userId = Expenses.getAuthedUserId();
        if (userId <= 0) {
            errorMessage = UpdateExpenseModal.Text.get("default-error");
        }

        System.out.println("isEmpty: " + errorMessage.isEmpty());
        if (errorMessage.isEmpty()) {
            float expenseResult = Float.parseFloat(expenseText);
            int categoryNumber = categories.get(selectedCategoryIndex).id;
            String descriptionResult = description == "" ? CreateExpenseModal.Text.get("no-description") : description;
            expensesService.updateExpense(
                    expenseId, userId, expenseResult, "$", descriptionResult, categoryNumber, date
            );
        } else {
            errorBlock.changeErrorText(errorMessage);
        }
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
            errorBlock.changeErrorText("");
            Expenses.getInstance().rerenderExpenses();
        });

        return container;
    }
}
