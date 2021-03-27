package com.company.core.services.database.types;

import com.company.core.services.database.Database;
import com.company.core.services.interfaces.RequestResultType;
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
    }

    @Override
    public RequestResultType init() {
        System.out.println("INIT");
        try {
            connection = DriverManager.getConnection(serverUrl, username, password);
            System.out.println(connection);
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
            if (requestType == Insert) {
                int rows = statement.executeUpdate();
                System.out.println("ROWS: " + rows);
                if (rows < 1) {
                    throw new Exception("Update was failed");
                }
                System.out.println("A new row was created!");
            } else if (requestType == Update) {

            } else if (requestType == Delete) {

            } else if (requestType == Get) {
                ResultSet result = statement.executeQuery(query);
                HashMap<String, String> returnData = new HashMap<String, String>();
                if (result.next()) {
                    String login = result.getString("name");
                    String password = result.getString("password");
                    returnData.put("name", login);
                    returnData.put("password", password);

                    return returnData;
                }
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        return null;
    }
}
