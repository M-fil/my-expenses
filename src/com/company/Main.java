package com.company;

import com.company.core.services.database.DatabaseHandler;
import com.company.core.services.database.types.MySQL;
import com.company.modules.MainFrame;
import com.company.modules.Registration.Registration;

public class Main {
    DatabaseHandler dbHandler;

    Main() {
        String url = "jdbc:mysql://localhost:3306/sys";
        String username = "";
        String password = "";

        MySQL mySqlDB = new MySQL(url, username, password);
        dbHandler = new DatabaseHandler().setDB(mySqlDB);
    }

    public static void main(String[] args) {
        Main main = new Main();
        MainFrame mainFrame = new MainFrame(MainFrame.DefaultWidth, MainFrame.DefaultHeight);
        mainFrame.init();
        mainFrame.renderPage(new Registration());
    }
}
