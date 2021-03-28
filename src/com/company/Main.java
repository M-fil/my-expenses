package com.company;

import com.company.core.constants.SecretProperties;
import com.company.core.services.database.DatabaseHandler;
import com.company.core.services.database.types.MySQL;
import com.company.core.services.propertiesReader.PropertiesReader;
import com.company.modules.ErrorPage.ErrorPage;
import com.company.modules.Expenses.Expenses;
import com.company.modules.MainFrame;
import com.company.modules.Registration.Registration;

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
            mainFrame.renderPage(new Expenses());
            isError = false;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
