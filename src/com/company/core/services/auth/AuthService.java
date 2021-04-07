package com.company.core.services.auth;

import com.company.core.services.database.DatabaseHandler;
import com.company.core.interfaces.RequestResultType;
import com.company.modules.Expenses.Expenses;
import com.company.modules.MainFrame;
import com.company.modules.Registration.Registration;

import java.sql.ResultSet;

public class AuthService {
    DatabaseHandler dbHandler;
    MainFrame mainFrame;
    private int authedUserId;

    public AuthService() {
        dbHandler = DatabaseHandler.getInstance();
        mainFrame = MainFrame.getInstance();
    }

    public boolean checkIfUserAlreadyExists(String login, String password) {
        try {
            String query = String.format("SELECT * FROM users WHERE BINARY `name`='%s' AND BINARY `password`='%s'", login, password);
            ResultSet result = dbHandler.get(query);
            if (result != null && result.next()) {
                authedUserId = result.getInt("id");
                String loginValue = result.getString("name");
                String passwordValue = result.getString("password");
                boolean isLoginsMatch = loginValue.equals(login);
                boolean isPasswordMatch = passwordValue.equals(password);

                return isLoginsMatch && isPasswordMatch;
            }

            return false;
        } catch (Exception error) {
            return false;
        }
    }

    private boolean checkIfLoginExists(String login) {
        try {
            String query = String.format("SELECT * FROM users WHERE BINARY `name`='%s'", login);
            ResultSet result = dbHandler.get(query);
            if (result != null && result.next()) {
                String loginValue = result.getString("name");

                return loginValue.equals(login);
            }

            return false;
        } catch (Exception error) {
            return  false;
        }
    }

    public RequestResultType signInWithEmailAndPassword(String login, String password, boolean checkCreds) {
        boolean isCredentialsMatch = checkIfUserAlreadyExists(login, password);
        if (!checkCreds) {
            isCredentialsMatch = true;
        }

        if (isCredentialsMatch) {
            mainFrame.renderPage(new Expenses(authedUserId));
            return RequestResultType.Success;
        } else {
            return RequestResultType.Error;
        }
    }

    public RequestResultType signUpWithEmailAndPassword(String login, String password) {
        String query = String.format("INSERT INTO users (name, password) VALUES ('%s', '%s')", login, password);
        boolean isUserWithTheSameLoginExists = checkIfLoginExists(login);

        if (!isUserWithTheSameLoginExists) {
            dbHandler.insert(query);
            return signInWithEmailAndPassword(login, password, false);
        } else {
            return RequestResultType.Error;
        }
    }

    public void signOut() {
        mainFrame.renderPage(new Registration());
    }
}
