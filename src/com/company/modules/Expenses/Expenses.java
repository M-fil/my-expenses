package com.company.modules.Expenses;

import com.company.core.components.PageEntity.PageEntity;

import javax.swing.*;

public class Expenses extends PageEntity {
    @Override
    public JPanel render() {
        JPanel container = new JPanel();
        JLabel label = new JLabel("TEXT");
        container.add(label);

        return container;
    }
}
