package configuration.settings;

import java.util.ResourceBundle;

/**
 * Database settings class
 */
public class DatabaseSettings {
    /**
     * Resource bundle
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    /**
     * Database URL
     */
    public static String URL = resourceBundle.getString("URL");
    /**
     * Database username
     */
    public static String USERNAME = resourceBundle.getString("USERNAME");
    /**
     * Database driver
     */
    public static String DRIVER = resourceBundle.getString("DRIVER");
    /**
     * Database password
     */
    public static String PASSWORD = resourceBundle.getString("PASSWORD");
}
