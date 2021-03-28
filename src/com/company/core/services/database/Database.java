package com.company.core.services.database;

import com.company.core.interfaces.RequestResultType;

import java.util.HashMap;

abstract public class Database {
    static public HashMap<String, String> Messages;

    public Database() {
        Database.Messages = new HashMap<String, String>();
    }

    public abstract RequestResultType init();
    public abstract Object makeRequest(String query, RequestTypes requestType);
}