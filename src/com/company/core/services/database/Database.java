package com.company.core.services.database;

import com.company.core.services.interfaces.RequestResultType;

abstract public class Database {
    public Database() {}

    public abstract RequestResultType init();
    public abstract Object makeRequest(String query, RequestTypes requestType);
}