package configuration.settings;

import java.util.ResourceBundle;

public class DatabaseSettings {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    public static String URL = resourceBundle.getString("URL");
    public static String USERNAME = resourceBundle.getString("USERNAME");
    public static String DRIVER = resourceBundle.getString("DRIVER");
    public static String PASSWORD = resourceBundle.getString("PASSWORD");
}
