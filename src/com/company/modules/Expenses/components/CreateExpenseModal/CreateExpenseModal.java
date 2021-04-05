package com.company.modules.Expenses.components.CreateExpenseModal;

import com.company.core.components.ComponentEntity.ComponentEntity;
import com.company.core.components.ErrorBlock.ErrorBlock;
import com.company.core.components.InputItem.InputItem;
import com.company.core.components.NumberTextField.NumberTextField;
import com.company.core.components.PageEntity.PageEntity;
import com.company.core.interfaces.ExpenseCategory;
import com.company.core.services.expenses.ExpensesService;
import com.company.modules.Expenses.Expenses;
import com.company.modules.MainFrame;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateExpenseModal extends ComponentEntity {
    static public int DEFAULT_DIALOG_WIDTH = 350;
    static public int DEFAULT_DIALOG_HEIGHT = 450;
    static private int DEFAULT_DIALOG_PADDING = 30;
    static public String DEFAULT_TITLE = "Create Expense";
    static protected float MINIMUM_EXPENSE_NUMBER = 0.1f;
    static protected float MAXIMUM_EXPENSE_NUMBER = 10_000;

    private int width;
    private int height;
    protected ExpensesService expensesService;
    private ArrayList<ExpenseCategory> categories;
    private ArrayList<String> categoriesNames;
    protected InputItem expenseInput;
    protected JButton createExpenseButton;
    protected JComboBox selectCategoryBox;
    protected InputItem descriptionInput;
    protected DatePicker datePicker;
    protected JDialog dialog;
    protected ErrorBlock errorBlock;

    public CreateExpenseModal(int width, int height) {
        CreateExpenseModal.Text.put("no-description", "No Description");
        CreateExpenseModal.Text.put("create-button-text", "Create");
        CreateExpenseModal.Text.put("description-label", "description");
        CreateExpenseModal.Text.put("expense-label", "expense number");
        CreateExpenseModal.Text.put("category-label", "select category");
        CreateExpenseModal.Text.put("expense-date-label", "select expense date");

        CreateExpenseModal.Text.put("expense-number-error", "The expense number should be between " + MINIMUM_EXPENSE_NUMBER + " and " + MAXIMUM_EXPENSE_NUMBER);
        CreateExpenseModal.Text.put("date-error", "An expense date should be less than " + LocalDate.now());
        CreateExpenseModal.Text.put("default-error", "Error while creating a new expense");

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

    protected String checkIfFieldsAreValid(
            String expenseText, LocalDate date
    ) {
        try {
            if (date == null) {
                throw new Exception(CreateExpenseModal.Text.get("date-error"));
            }
            if (expenseText.trim() == "") {
                throw new Exception(CreateExpenseModal.Text.get("expense-number-error"));
            }

            float expenseResult = Float.parseFloat(expenseText);
            if (expenseResult < MINIMUM_EXPENSE_NUMBER || expenseResult > MAXIMUM_EXPENSE_NUMBER) {
                throw new Exception(CreateExpenseModal.Text.get("expense-number-error"));
            }
            if (date.isAfter(LocalDate.now())) {
                throw new Exception(CreateExpenseModal.Text.get("date-error"));
            }

            return "";
        } catch (Exception error) {
            return error.getLocalizedMessage();
        }
    }

    private void createExpense(
            String expenseText, String description, int selectedCategoryIndex, LocalDate date
    ) {
        String errorMessage = checkIfFieldsAreValid(expenseText, date);
        int userId = Expenses.getAuthedUserId();
        System.out.println();
        if (userId <= 0) {
            errorMessage = CreateExpenseModal.Text.get("default-error");
        }

        System.out.println("create userId: " + Expenses.getAuthedUserId());
        System.out.println("is empty: " + errorMessage.isEmpty());
        if (errorMessage.isEmpty()) {
            float expenseResult = Float.parseFloat(expenseText);
            int categoryNumber = categories.get(selectedCategoryIndex).id;
            String descriptionResult = description == "" ? CreateExpenseModal.Text.get("no-description") : description;

            expensesService.createNewExpense(
                    userId, expenseResult, "$", descriptionResult, categoryNumber, date
            );
        } else {
            errorBlock.changeErrorText(errorMessage);
        }
    }

    private <T extends JComponent> JPanel createElementWithLabel(T component, String label) {
        JPanel container = new JPanel(new GridLayout(2, 1));
        JLabel comboBoxLabel = new JLabel(label);

        container.add(comboBoxLabel);
        container.add(component);

        return container;
    }

    public void repaintDialog() {
        dialog.repaint();
        dialog.revalidate();
    }

    public void show() {
        dialog.setVisible(true);
    }

    @Override
    public JPanel render() {
        JFrame frame = MainFrame.getInstance().getFrame();
        JPanel wrapper = new JPanel();
        PageEntity.setTheSamePaddingForAllSides(CreateExpenseModal.DEFAULT_DIALOG_PADDING, wrapper);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        dialog = new JDialog(frame, CreateExpenseModal.DEFAULT_TITLE, Dialog.ModalityType.DOCUMENT_MODAL);
        dialog.setBounds(150, 150, width, height);
        dialog.setResizable(false);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dialog.setVisible(false);
            }
        });

        JTextField numberTextField = new NumberTextField().getTextField();
        descriptionInput = new InputItem(new JTextField(), CreateExpenseModal.Text.get("description-label"), "description");
        expenseInput = new InputItem(numberTextField, CreateExpenseModal.Text.get("expense-label"), "expense");
        JPanel createExpenseButtonContainer = new JPanel();
        createExpenseButton = new JButton(CreateExpenseModal.Text.get("create-button-text"));
        selectCategoryBox = new JComboBox(categoriesNames.toArray());
        JPanel comboBoxContainer = createElementWithLabel(selectCategoryBox, CreateExpenseModal.Text.get("category-label"));
        datePicker = new DatePicker();
        JPanel datePickerContainer = createElementWithLabel(datePicker, CreateExpenseModal.Text.get("expense-date-label"));
        errorBlock = new ErrorBlock("", 200, "center", Color.RED);
        JPanel errorBlockElement = new JPanel();
        errorBlockElement.add(errorBlock.render());

        ComponentEntity.removeButtonAllActionListeners( createExpenseButton);
        createExpenseButton.addActionListener((event) -> {
            createExpense(
                    expenseInput.getTextValue(),
                    descriptionInput.getTextValue(),
                    selectCategoryBox.getSelectedIndex(),
                    datePicker.getDate()
            );
            errorBlock.changeErrorText("");
            Expenses.getInstance().rerenderExpenses();
        });

        createExpenseButtonContainer.add(createExpenseButton);
        wrapper.add(descriptionInput.render());
        wrapper.add(expenseInput.render());
        wrapper.add(comboBoxContainer);
        wrapper.add(datePickerContainer);
        wrapper.add(createExpenseButtonContainer);
        wrapper.add(errorBlockElement);
        dialog.add(wrapper);
        repaintDialog();

        return new JPanel();
    }
}
