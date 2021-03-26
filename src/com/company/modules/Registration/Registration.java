package com.company.modules.Registration;
import com.company.core.components.PageEntity.PageEntity;
import com.company.core.services.database.DatabaseHandler;

import javax.swing.*;
import java.awt.*;

public class Registration extends PageEntity {
    JTextField loginTextField;
    JTextField passwordTextField;
    DatabaseHandler dbHandler;

    public Registration() {
        super();
        Registration.Text.put("login-label-text", "Login");
        Registration.Text.put("password-label-text", "Password");
        Registration.Text.put("sign-in-button-text", "Sign in");
        dbHandler = DatabaseHandler.getInstance();
    }

    private JTextField renderInputItem(
            String labelText, String textFieldName,
            int inputWidth, int inputHeight, JPanel parent
    ) {
        JPanel container = new JPanel();
        JTextField textField = new JTextField();
        JLabel label = new JLabel(labelText);

        textField.setName(textFieldName);
        textField.setPreferredSize(new Dimension(inputWidth, inputHeight));

        container.add(label);
        container.add(textField);
        parent.add(container);

        return textField;
    }

    @Override
    public JPanel render() {
        JPanel wrapper = new JPanel();
        pageWrapper.setBackground(Color.BLACK);
        pageWrapper.setLayout(new BorderLayout());
        wrapper.setBackground(Color.YELLOW);

        String loginLabel = Registration.Text.get("login-label-text");
        String passwordLabel = Registration.Text.get("password-label-text");
        loginTextField = renderInputItem(loginLabel, "login", 100, 30, wrapper);
        passwordTextField = renderInputItem(passwordLabel, "password", 100, 30, wrapper);
        JButton submitButton = new JButton(Registration.Text.get("sign-in-button-text"));
        submitButton.addActionListener((event) -> {
            String login = loginTextField.getText();
            String password = passwordTextField.getText();
            String query = String.format("INSERT INTO users (name, password) VALUES ('%s', '%s')", login, password);
            dbHandler.insert(query);
            System.out.println("Success!");
        });

        wrapper.add(submitButton);
        pageWrapper.add(wrapper);
        return this.pageWrapper;
    }
}
