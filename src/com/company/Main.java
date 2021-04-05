package com.company;

import com.company.core.constants.SecretProperties;
import com.company.core.services.database.DatabaseHandler;
import com.company.core.services.database.types.MySQL;
import com.company.core.services.propertiesReader.PropertiesReader;
import com.company.modules.ErrorPage.ErrorPage;
import com.company.modules.Expenses.Expenses;
import com.company.modules.MainFrame;
import com.company.modules.Registration.Registration;

import javax.swing.*;
import java.awt.*;

public class Main {
    DatabaseHandler dbHandler;
    boolean isError;

    Main() {
        isError = false;
        String url = PropertiesReader.getApiKey(SecretProperties.MYSQL_SERVER_URL);
        String username = PropertiesReader.getApiKey(SecretProperties.MYSQL_SERVER_USERNAME);
        String password = PropertiesReader.getApiKey(SecretProperties.MYSQL_SERVER_PASSWORD);

        MySQL mySqlDB = new MySQL(url, username, password);
        MainFrame mainFrame = MainFrame.getInstance();

        if (mySqlDB == null) {
            mainFrame.renderPage(new ErrorPage(MySQL.Messages.get("db-init-error")));
            isError = true;
        } else {
            dbHandler = new DatabaseHandler().setDB(mySqlDB);
            mainFrame.renderPage(new Expenses(1));
            isError = false;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (int i = 0; i < 10; i++) {
            panel.add(new JButton("Hello-" + i));
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 30, 300, 50);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 400));
        contentPane.add(scrollPane);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        new Main();
    }
}
