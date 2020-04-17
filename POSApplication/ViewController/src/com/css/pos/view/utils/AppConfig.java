package com.css.pos.view.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class AppConfig {
    //this method takes the KEY from the properties file and return the value
    public static String getProperty(String property) throws IOException {
        InputStream inputStream = null;
        String propValue = "";
        try {
            Properties prop = new Properties();
            File file = new File("pos-common-resources/application.properties");
            inputStream = new FileInputStream(file);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file not found in the folder pos-common-resources");
            }
            propValue = prop.getProperty(property.toLowerCase());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return propValue;
    }
}
