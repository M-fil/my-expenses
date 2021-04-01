package com.company.core.components.InputItem;

import com.company.core.components.ComponentEntity.ComponentEntity;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class InputItem extends ComponentEntity {
    private JTextComponent textComponent;
    private String labelText;
    private String textFieldName;

    public InputItem(JTextComponent textComponent, String labelText, String textFieldName) {
        this.textComponent = textComponent;
        this.labelText = labelText;
        this.textFieldName = textFieldName;
    }

    public String getTextValue() {
        return textComponent.getText();
    }

    public void setTextValue(String text) {
        textComponent.setText(text);
    }

    @Override
    public JPanel render() {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel(labelText);
        textComponent.setName(textFieldName);

        container.add(label);
        container.add(textComponent);

        return container;
    }
}
