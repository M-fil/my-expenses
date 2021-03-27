package com.company.core.services.propertiesReader;

import java.io.FileInputStream;
import java.util.Properties;

public final class PropertiesReader {
    private static String API_FILE_NAME = "apikey.properties";

    public static String getApiKey(String property){
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(PropertiesReader.API_FILE_NAME));

            return properties.getProperty(property);
        } catch (Exception e) {
            return "";
        }
    }
}