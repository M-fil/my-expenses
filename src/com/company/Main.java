package com.company;

import com.company.core.constants.SecretProperties;
import com.company.core.services.database.DatabaseHandler;
import com.company.core.services.database.types.MySQL;
import com.company.core.services.propertiesReader.PropertiesReader;
import com.company.modules.MainFrame;
import com.company.modules.Registration.Registration;

public class Main {
    DatabaseHandler dbHandler;

    Main() {
        String url = PropertiesReader.getApiKey(SecretProperties.MYSQL_SERVER_URL);
        String username = PropertiesReader.getApiKey(SecretProperties.MYSQL_SERVER_USERNAME);
        String password = PropertiesReader.getApiKey(SecretProperties.MYSQL_SERVER_PASSWORD);

        MySQL mySqlDB = new MySQL(url, username, password);
        dbHandler = new DatabaseHandler().setDB(mySqlDB);
    }

    public static void main(String[] args) {
        new Main();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.renderPage(new Registration());
    }
}
