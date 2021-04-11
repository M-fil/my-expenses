package com.company.modules.Expenses.components.ExpenseItem;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.Expenses;
import com.company.modules.Expenses.components.Modals.UpdateExpenseModal;
import com.company.modules.MainFrame;

import javax.swing.*;
import java.awt.*;
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
        JPanel expensesContainer = new JPanel();
        expensesContainer.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        JPanel expensesWrapper = new JPanel(new GridLayout(0, 1));

        String extraInfoLabel = amount + currency + "/" + category.getName() + "/" + creationDate;
        String topText = "<html><p>" + extraInfoLabel + "</p></html>";
        String descriptionHTML = "<html><p style=\"width: 300px;\">" + description + "</p></html>";

        JLabel priceAndCategoryLabel = new JLabel(topText);
        Font font = priceAndCategoryLabel.getFont();
        priceAndCategoryLabel.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
        JLabel descriptionLabel = new JLabel(descriptionHTML);

        editButton = new JButton(ExpenseItem.Text.get("edit-button-text"));
        deleteButton = new JButton(ExpenseItem.Text.get("delete-button-text"));
        editButton.addActionListener((event) -> {
            UpdateExpenseModal updateModal = new UpdateExpenseModal(
                    UpdateExpenseModal.DEFAULT_DIALOG_WIDTH, UpdateExpenseModal.DEFAULT_DIALOG_HEIGHT
            ).setExpenseValues(id, amount, creationDate, category.getId(), description);
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
        JPanel buttonsContainer = new JPanel();
        JPanel labelsContainer = new JPanel(new BorderLayout());

        labelsContainer.add(priceAndCategoryLabel, BorderLayout.NORTH);
        labelsContainer.add(descriptionLabel);
        buttonsContainer.add(editButton);
        buttonsContainer.add(deleteButton);
        expensesWrapper.add(labelsContainer);
        expensesWrapper.add(buttonsContainer);
        expensesContainer.add(expensesWrapper);

        return expensesContainer;
    }
}
