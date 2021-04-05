package com.company.modules.Expenses.components.ExpenseItem;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.Expenses;
import com.company.modules.Expenses.components.CreateExpenseModal.UpdateExpenseModal;
import com.company.modules.MainFrame;

import javax.swing.*;
import java.time.LocalDate;

public class ExpenseItem extends ComponentEntity {
    private String description;
    private float amount;
    private String currency;
    private int id;
    private LocalDate creationDate;
    private ExpenseCategory category;
    private JButton editButton;
    private JButton deleteButton;

    public ExpenseItem(int id, float amount, String currency, String description, LocalDate creationDate) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.creationDate = creationDate;

        ExpenseItem.Text.put("edit-button-text", "Edit");
        ExpenseItem.Text.put("delete-button-text", "Delete");
        ExpenseItem.Text.put("delete-expense-question", "Are you sure that you want to delete this expense?");
    }

    public ExpenseItem setCategory(ExpenseCategory category) {
        this.category = category;
        return this;
    }

    @Override
    public JPanel render() {
        JPanel expenseContainer = new JPanel();
        editButton = new JButton(ExpenseItem.Text.get("edit-button-text"));
        deleteButton = new JButton(ExpenseItem.Text.get("delete-button-text"));
        JLabel label = new JLabel(amount + currency + "/" + description + "/" + category.name);
        editButton.addActionListener((event) -> {
            UpdateExpenseModal updateModal = new UpdateExpenseModal(
                    UpdateExpenseModal.DEFAULT_DIALOG_WIDTH, UpdateExpenseModal.DEFAULT_DIALOG_HEIGHT
            ).setExpenseValues(id, amount, creationDate, category.id, description);
            updateModal.render();
            updateModal.show();
        });
        deleteButton.addActionListener((event) -> {
            JFrame parent = MainFrame.getInstance().getFrame();
            int result = JOptionPane.showConfirmDialog(parent, ExpenseItem.Text.get("delete-expense-question"));
            boolean isConfirmed = result == 0;
            if (isConfirmed) {
                new ExpensesService().deleteExpense(id);
                Expenses.getInstance().rerenderExpenses();
            }
        });

        expenseContainer.add(label);
        expenseContainer.add(editButton);
        expenseContainer.add(deleteButton);

        return expenseContainer;
    }
}
