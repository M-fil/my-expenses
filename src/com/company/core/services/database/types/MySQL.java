package com.company.core.services.database.types;

import com.company.core.services.database.Database;
import com.company.core.interfaces.RequestResultType;
import com.company.core.services.database.RequestTypes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import static com.company.core.services.database.RequestTypes.*;

public class MySQL extends Database {
    private String serverUrl;
    private String username;
    private String password;
    private Connection connection;

    public MySQL(String serverUrl, String username, String password) {
        this.serverUrl = serverUrl;
        this.username = username;
        this.password = password;
        MySQL.Messages.put("db-init-error", "There is an error during db initialization");
    }

    @Override
    public RequestResultType init() {
        try {
            connection = DriverManager.getConnection(serverUrl, username, password);
            return RequestResultType.Success;
        } catch (Exception error) {
            System.out.println("Error: " + error.getMessage());
            return RequestResultType.Error;
        }
    }

    @Override
    public Object makeRequest(String query, RequestTypes requestType) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            if (requestType == Insert || requestType == Update) {
                int rows = statement.executeUpdate();
                if (rows < 1) {
                    throw new Exception("Update was failed");
                }
                HashMap<String, Integer> returnData = new HashMap<String, Integer>();
                returnData.put("result", rows);

                return returnData;
            } else if (requestType == Delete) {

            } else if (requestType == Get) {
                ResultSet result = statement.executeQuery(query);
                return result;
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        return null;
    }
}
