package com.company.modules.Registration;
import com.company.core.components.ErrorBlock.ErrorBlock;
import com.company.core.components.InputItem.InputItem;
import com.company.core.components.PageEntity.PageEntity;
import com.company.core.services.auth.AuthService;
import com.company.core.interfaces.RequestResultType;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class Registration extends PageEntity {
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private boolean isSignUpForm;
    private AuthService authService;
    private ErrorBlock errorBlock;

    private static int DEFAULT_WRAPPER_PADDING = 20;
    private static int MINIMUM_LOGIN_LENGTH = 3;
    private static int MAXIMUM_LOGIN_LENGTH = 40;
    private static int MINIMUM_PASSWORD_LENGTH = 6;
    private static int MAXIMUM_PASSWORD_LENGTH = 16;

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
        Registration.Text.put("empty-values", "Password and login should not be empty.");
        Registration.Text.put("password-validation-error", "The password length should be from " + MINIMUM_PASSWORD_LENGTH + " to " + MAXIMUM_PASSWORD_LENGTH + " symbols.");
        Registration.Text.put("login-validation-error", "The login length should be from " + MINIMUM_LOGIN_LENGTH + " to " + MAXIMUM_LOGIN_LENGTH + " symbols.");

        isSignUpForm = true;
        authService = new AuthService();
        errorBlock = new ErrorBlock("", 200, "center", Color.RED);
    }

    private void submitCredentials() {
        try {
            String login = loginTextField.getText().trim();
            String password = passwordTextField.getText().trim();
            String errorMessage = "";
            RequestResultType result = null;

            if (login.isEmpty() || password.isEmpty()) {
                throw new Exception(Registration.Text.get("empty-values"));
            }
            if (login.length() < MINIMUM_LOGIN_LENGTH || login.length() > MAXIMUM_LOGIN_LENGTH) {
                throw new Exception(Registration.Text.get("login-validation-error"));
            }
            if (password.length() < MINIMUM_PASSWORD_LENGTH || password.length() > MAXIMUM_PASSWORD_LENGTH) {
                throw new Exception(Registration.Text.get("password-validation-error"));
            }

            if (isSignUpForm) {
                result = authService.signUpWithEmailAndPassword(login, password);
                errorMessage = Registration.Text.get("sign-up-error");
            } else {
                result = authService.signInWithEmailAndPassword(login, password, true);
                errorMessage = Registration.Text.get("sign-in-error");
            }

            if (result == RequestResultType.Success) {
                loginTextField.setText("");
                passwordTextField.setText("");
            } else {
                throw new Exception(errorMessage);
            }
        } catch (Exception error) {
            errorBlock.changeErrorText(error.getMessage());
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
            JPanel parent, boolean isPassword
    ) {
        JTextComponent textField = null;
        if (isPassword) {
            textField = new JPasswordField();
        } else {
            textField = new JTextField();
        }
        InputItem inputItem = new InputItem(textField, labelText, textFieldName);
        parent.add(inputItem.render());

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
        PageEntity.setTheSamePaddingForAllSides(DEFAULT_WRAPPER_PADDING, wrapper);
        borderWrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        String loginLabel = Registration.Text.get("login-label-text");
        String passwordLabel = Registration.Text.get("password-label-text");
        loginTextField = (JTextField) renderInputItem(
                loginLabel, "login", wrapper, false
        );
        passwordTextField = (JPasswordField) renderInputItem(
                passwordLabel, "password", wrapper, true
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
