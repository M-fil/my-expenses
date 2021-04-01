package com.company.core.components.NumberTextField;

import com.company.core.components.ComponentEntity.ComponentEntity;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumberTextField extends ComponentEntity {
    static private int BACKSPACE_CODE = 8;
    static private int DOT_CODE = 46;
    private JTextField textField;

    public NumberTextField() {
        render();
    }

    public JTextField getTextField() {
        return textField;
    }

    @Override
    public JPanel render() {
        textField = new JTextField(25);
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                boolean isBackspaceWasPressed = keyEvent.getKeyCode() == NumberTextField.BACKSPACE_CODE;
                boolean isDotPressed = keyEvent.getKeyCode() == DOT_CODE;
                boolean isNumberPressed = keyEvent.getKeyChar() >= '0' && keyEvent.getKeyChar() <= '9';
                if (isNumberPressed || isBackspaceWasPressed || isDotPressed) {
                    textField.setEditable(true);
                } else {
                    textField.setEditable(false);
                }
            }
        });

        return new JPanel();
    }
}
