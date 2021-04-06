package com.company.core.components.ErrorBlock;

import com.company.core.components.ComponentEntity.ComponentEntity;

import javax.swing.*;
import java.awt.*;

public class ErrorBlock extends ComponentEntity {
    private String errorText;
    private int maxWidth;
    private String textAlign;
    private Color color;
    private JLabel errorLabel;

    public ErrorBlock(String errorText, int maxWidth, String textAlign, Color color) {
        this.errorText = errorText;
        this.maxWidth = maxWidth;
        this.textAlign = textAlign;
        this.color = color;
    }

    private String insertTextInHTML(String text) {
        String stringStart = "<html><p style=\"width: " + maxWidth + "px;" + " text-align: " + textAlign + "\">";
        String stringEnd = "</p></html>";

        return stringStart + text + stringEnd;
    }

    public void changeErrorText(String newErrorText) {
        String htmlString = insertTextInHTML(newErrorText);
        errorLabel.setText(htmlString);
        errorLabel.repaint();
        errorLabel.revalidate();
    }

    @Override
    public JPanel render() {
        JPanel panel = new JPanel();
        errorLabel = new JLabel(insertTextInHTML(errorText));
        errorLabel.setForeground(color);
        panel.add(errorLabel);

        return panel;
    }
}
