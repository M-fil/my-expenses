package com.company.core.services.auth;

import com.company.core.services.database.DatabaseHandler;
import com.company.core.services.interfaces.RequestResultType;
import com.company.modules.Expenses.Expenses;
import com.company.modules.MainFrame;
import com.company.modules.Registration.Registration;

import java.util.HashMap;

public class AuthService {
    DatabaseHandler dbHandler;
    MainFrame mainFrame;

    public AuthService() {
        dbHandler = DatabaseHandler.getInstance();
        mainFrame = MainFrame.getInstance();
    }

    public boolean checkIfUserAlreadyExists(String login, String password) {
        String query = String.format("SELECT * FROM users WHERE `name`='%s' AND `password`='%s'", login, password);
        HashMap<String, String> result = dbHandler.get(query);
        boolean isCredentialsMatch = result != null;

        return  isCredentialsMatch;
    }

    public RequestResultType signInWithEmailAndPassword(String login, String password) {
        boolean isCredentialsMatch = checkIfUserAlreadyExists(login, password);

        if (isCredentialsMatch) {
            mainFrame.renderPage(new Expenses());
            return RequestResultType.Success;
        } else {
            return RequestResultType.Error;
        }
    }

    public RequestResultType signUpWithEmailAndPassword(String login, String password) {
        String query = String.format("INSERT INTO users (name, password) VALUES ('%s', '%s')", login, password);
        boolean isCredentialsMatch = checkIfUserAlreadyExists(login, password);
        System.out.println("isCredentialsMatch " + isCredentialsMatch);
        if (!isCredentialsMatch) {
            dbHandler.insert(query);
            return signInWithEmailAndPassword(login, password);
        } else {
            return RequestResultType.Error;
        }
    }

    public void signOut() {
        mainFrame.renderPage(new Registration());
    }
}
