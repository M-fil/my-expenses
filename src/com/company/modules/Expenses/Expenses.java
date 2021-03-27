package com.company.modules.Expenses;

import com.company.core.components.PageEntity.PageEntity;
import com.company.core.services.auth.AuthService;

import javax.swing.*;

public class Expenses extends PageEntity {
    AuthService authService;

    public Expenses() {
        super();
        authService = new AuthService();
    }

    @Override
    public JPanel render() {
        JPanel container = new JPanel();
        JLabel label = new JLabel("TEXT");
        JButton signOutButton = new JButton("Sign out");
        signOutButton.addActionListener((event) -> {
            authService.signOut();
        });

        container.add(label);
        container.add(signOutButton);

        return container;
    }
}
