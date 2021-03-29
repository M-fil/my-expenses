package com.company.modules.Expenses.components.CreateExpenseModal;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.components.InputItem.InputItem;
import com.company.core.components.NumberTextField.NumberTextField;
import com.company.core.components.PageEntity.PageEntity;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.MainFrame;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateExpenseModal extends ComponentEntity {
    static public int DEFAULT_DIALOG_WIDTH = 350;
    static public int DEFAULT_DIALOG_HEIGHT = 350;
    static private int DEFAULT_DIALOG_PADDING = 30;
    static public String DEFAULT_TITLE = "Create Expense";

    private int width;
    private int height;
    private ExpensesService expensesService;
    private ArrayList<ExpenseCategory> categories;
    private ArrayList<String> categoriesNames;

    public CreateExpenseModal(int width, int height) {
        CreateExpenseModal.Text.put("no-description", "No Description");
        CreateExpenseModal.Text.put("create-button-text", "Create");
        CreateExpenseModal.Text.put("description-label", "description");
        CreateExpenseModal.Text.put("expense-label", "expense number");
        CreateExpenseModal.Text.put("category-label", "select category");
        CreateExpenseModal.Text.put("expense-date-label", "select expense date");

        this.width = width;
        this.height = height;

        expensesService = new ExpensesService();
        categoriesNames = new ArrayList<String>();
        categories = new ArrayList<ExpenseCategory>();
    }

    public void setCategories(ArrayList<ExpenseCategory> categories) {
        this.categories = categories;
        for (ExpenseCategory category : categories) {
            categoriesNames.add(category.name);
        }
    }

    private void createExpense(
            String expenseText, String description, int selectedCategoryIndex, LocalDate date
    ) {
        int expenseResult = Integer.parseInt(expenseText);
        int categoryNumber = categories.get(selectedCategoryIndex).id;
        String descriptionResult = description == "" ? CreateExpenseModal.Text.get("no-description") : description;
        HashMap<String, Integer> result = expensesService.createNewExpense(1, expenseResult, "$", descriptionResult, categoryNumber, date);
    }

    private <T extends JComponent> JPanel createElementWithLabel(T component, String label) {
        JPanel container = new JPanel(new GridLayout(2, 1));
        JLabel comboBoxLabel = new JLabel(label);

        container.add(comboBoxLabel);
        container.add(component);

        return container;
    }

    @Override
    public JPanel render() {
        JFrame frame = MainFrame.getInstance().getFrame();
        JPanel wrapper = new JPanel();
        PageEntity.setTheSamePaddingForAllSides(CreateExpenseModal.DEFAULT_DIALOG_PADDING, wrapper);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        JDialog dialog = new JDialog(frame, CreateExpenseModal.DEFAULT_TITLE, Dialog.ModalityType.DOCUMENT_MODAL);
        dialog.setBounds(150, 150, width, height);
        dialog.setResizable(false);

        JTextField numberTextField = new NumberTextField().getTextField();
        InputItem descriptionInput = new InputItem(new JTextField(), CreateExpenseModal.Text.get("description-label"), "description");
        InputItem expenseInput = new InputItem(numberTextField, CreateExpenseModal.Text.get("expense-label"), "expense");
        JPanel createExpenseButtonContainer = new JPanel();
        JButton createExpenseButton = new JButton(CreateExpenseModal.Text.get("create-button-text"));
        JComboBox selectCategoryBox = new JComboBox(categoriesNames.toArray());
        JPanel comboBoxContainer = createElementWithLabel(selectCategoryBox, CreateExpenseModal.Text.get("category-label"));
        DatePicker datePicker = new DatePicker();
        JPanel datePickerContainer = createElementWithLabel(datePicker, CreateExpenseModal.Text.get("expense-date-label"));

        createExpenseButton.addActionListener((event) -> {
            createExpense(
                    expenseInput.getTextValue(),
                    descriptionInput.getTextValue(),
                    selectCategoryBox.getSelectedIndex(),
                    datePicker.getDate()
            );
        });

        createExpenseButtonContainer.add(createExpenseButton);
        wrapper.add(descriptionInput.render());
        wrapper.add(expenseInput.render());
        wrapper.add(comboBoxContainer);
        wrapper.add(datePickerContainer);
        wrapper.add(createExpenseButtonContainer);
        dialog.add(wrapper);

        dialog.setVisible(true);
        dialog.repaint();
        dialog.revalidate();

        return new JPanel();
    }
}
