package com.company.core.services.database;

import com.company.core.interfaces.RequestResultType;

import java.sql.ResultSet;
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
        RequestResultType result = db.init();
        if (result == RequestResultType.Success) {
            DatabaseHandler.db = db;
            return DatabaseHandler.getInstance();
        }

        return null;
    }

    private Object makeRequest(String query, RequestTypes type) {
        try {
            if (DatabaseHandler.db == null) {
                throw new Exception("You must specify database via setDB method");
            }
            return DatabaseHandler.db.makeRequest(query, type);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        return null;
    }

    public HashMap<String, Integer> insert(String query) {
        return (HashMap<String, Integer>) makeRequest(query, RequestTypes.Insert);
    }

    public void delete(String query) {
        makeRequest(query, RequestTypes.Delete);
    }

    public HashMap<String, Integer> update(String query) {
        return (HashMap<String, Integer>) makeRequest(query, RequestTypes.Update);
    }

    public ResultSet get(String query) {
        Object result = makeRequest(query, RequestTypes.Get);
        return (ResultSet) result;
    }
}
