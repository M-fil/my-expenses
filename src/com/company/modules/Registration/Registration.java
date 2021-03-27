package com.company.modules.Registration;
import com.company.core.components.PageEntity.PageEntity;
import com.company.core.services.auth.AuthService;
import com.company.core.services.interfaces.RequestResultType;

import javax.swing.*;
import java.awt.*;

public class Registration extends PageEntity {
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private boolean isSignUpForm;
    private AuthService authService;
    private JLabel errorLabel;

    public Registration() {
        super();
        Registration.Text.put("login-label-text", "Login");
        Registration.Text.put("password-label-text", "Password");
        Registration.Text.put("sign-in-button-text", "Sign in");
        Registration.Text.put("sign-up-button-text", "Sign up");
        Registration.Text.put("switch-to-sign-in", "Do you have an account? Sign in now!");
        Registration.Text.put("switch-to-sign-up", "Don't have an account? Sign up now!");
        Registration.Text.put("sign-up-error", "The user with these login and password already exists");
        Registration.Text.put("sign-in-error", "There is no user with these login and password");
        isSignUpForm = true;
        authService = new AuthService();
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

    private void showErrorMessage(String errorMessage, RequestResultType result) {
        if (result == RequestResultType.Error) {
            errorLabel.setText(errorMessage);
        }
    }

    private void submitCredentials() {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        RequestResultType result = null;

        if (isSignUpForm) {
            result = authService.signUpWithEmailAndPassword(login, password);
            showErrorMessage(Registration.Text.get("sign-up-error"), result);
        } else {
            result = authService.signInWithEmailAndPassword(login, password);
            showErrorMessage(Registration.Text.get("sign-in-error"), result);
        }

        if (result == RequestResultType.Success) {
            loginTextField.setText("");
            passwordTextField.setText("");
        }
    }

    private void switchAuthButtons(JButton switchButton, JButton submitButton) {
        isSignUpForm = !isSignUpForm;
        String switchButtonText = isSignUpForm
                ? Registration.Text.get("switch-to-sign-up")
                : Registration.Text.get("switch-to-sign-in");
        String submitButtonText = isSignUpForm
                ? Registration.Text.get("sign-up-button-text")
                : Registration.Text.get("sign-in-button-text");
        switchButton.setText(switchButtonText);
        submitButton.setText(submitButtonText);
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
        JButton submitButton = new JButton(Registration.Text.get("sign-up-button-text"));
        submitButton.addActionListener((event) -> submitCredentials());

        JButton switchButton = new JButton(Registration.Text.get("switch-to-sign-up"));
        switchButton.addActionListener((event) -> switchAuthButtons(switchButton, submitButton));
        switchButton.setBorderPainted(false);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        wrapper.add(submitButton);
        wrapper.add(errorLabel);
        wrapper.add(switchButton);
        pageWrapper.add(wrapper);
        return this.pageWrapper;
    }
}
