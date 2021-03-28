package com.company.core.services.database;

import com.company.core.services.interfaces.RequestResultType;

import java.util.HashMap;

public class DatabaseHandler {
    private static DatabaseHandler instance = null;
    private static Database db = null;

    public static DatabaseHandler getInstance() {
        if (DatabaseHandler.instance == null) {
            DatabaseHandler.instance = new DatabaseHandler();
        }

        return DatabaseHandler.instance;
    }

    public DatabaseHandler setDB(Database db) {
        System.out.println("DB" + db);
        RequestResultType result = db.init();
        System.out.println("RESULT " + result);
        if (result == RequestResultType.Success) {
            DatabaseHandler.db = db;
            return DatabaseHandler.getInstance();
        }

        return null;
    }

    private Object makeRequest(String query, RequestTypes type) {
        try {
            System.out.println("DB " + DatabaseHandler.db);
            if (DatabaseHandler.db == null) {
                throw new Exception("You must specify database via setDB method");
            }
            return DatabaseHandler.db.makeRequest(query, type);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        return null;
    }

    public void insert(String query) {
        makeRequest(query, RequestTypes.Insert);
    }

    public void delete(String query) {
        makeRequest(query, RequestTypes.Delete);
    }

    public void update(String query) {
        makeRequest(query, RequestTypes.Update);
    }

    public HashMap<String, String> get(String query) {
        Object result = makeRequest(query, RequestTypes.Get);
        return (HashMap<String, String>)result;
    }
}
