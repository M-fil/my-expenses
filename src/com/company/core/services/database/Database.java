package com.company.core.services.database;

import com.company.core.services.database.types.RequestResultType;

abstract public class Database {
    public Database() {}

    public abstract RequestResultType init();
    public abstract void makeRequest(String query, RequestTypes requestType);
}