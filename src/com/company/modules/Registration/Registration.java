package com.company.modules.Registration;
import com.company.core.components.ErrorBlock.ErrorBlock;
import com.company.core.components.PageEntity.PageEntity;
import com.company.core.services.auth.AuthService;
import com.company.core.services.interfaces.RequestResultType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class Registration extends PageEntity {
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private boolean isSignUpForm;
    private AuthService authService;
    private ErrorBlock errorBlock;

    private static int DEFAULT_WRAPPER_PADDING = 20;

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
        errorBlock = new ErrorBlock("", 200, "center", Color.RED);
    }

    private void showErrorMessage(String errorMessage, RequestResultType result) {
        if (result == RequestResultType.Error) {
            errorBlock.changeErrorText(errorMessage);
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

    private JTextComponent renderInputItem(
            String labelText, String textFieldName,
            int inputWidth, int inputHeight, JPanel parent,
            boolean isPassword
    ) {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(2, 1));
        JTextComponent textField = null;
        if (isPassword) {
            textField = new JPasswordField();
        } else {
            textField = new JTextField();
        }
        JLabel label = new JLabel(labelText);
        textField.setName(textFieldName);
        textField.setMaximumSize(new Dimension(inputWidth, inputHeight));

        container.add(label);
        container.add(textField);
        parent.add(container);

        return textField;
    }

    private <T extends Component> JPanel createElementWithPanelWrapper(T element) {
        JPanel panel = new JPanel();
        panel.add(element);

        return  panel;
    }

    @Override
    public JPanel render() {
        JPanel wrapper = new JPanel();
        JPanel borderWrapper = new JPanel();

        pageWrapper.setLayout(new GridBagLayout());
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBorder(new EmptyBorder(DEFAULT_WRAPPER_PADDING, DEFAULT_WRAPPER_PADDING, DEFAULT_WRAPPER_PADDING, DEFAULT_WRAPPER_PADDING));
        borderWrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        String loginLabel = Registration.Text.get("login-label-text");
        String passwordLabel = Registration.Text.get("password-label-text");
        loginTextField = (JTextField) renderInputItem(
                loginLabel, "login", 100, 30, wrapper, false
        );
        passwordTextField = (JPasswordField) renderInputItem(
                passwordLabel, "password", 100, 30, wrapper, true
        );

        JButton submitButton = new JButton(Registration.Text.get("sign-up-button-text"));
        submitButton.addActionListener((event) -> submitCredentials());

        JButton switchButton = new JButton(Registration.Text.get("switch-to-sign-up"));
        switchButton.setBorderPainted(false);
        switchButton.addActionListener((event) -> switchAuthButtons(switchButton, submitButton));

        JPanel errorBlockHTML = errorBlock.render();

        JPanel buttonsPanel1 = createElementWithPanelWrapper(submitButton);
        JPanel buttonsPanel2 = createElementWithPanelWrapper(switchButton);
        JPanel errorMessagePanel = createElementWithPanelWrapper(errorBlockHTML);

        wrapper.add(buttonsPanel1);
        wrapper.add(errorMessagePanel);
        wrapper.add(buttonsPanel2);
        borderWrapper.add(wrapper);
        pageWrapper.add(borderWrapper);

        return this.pageWrapper;
    }
}
