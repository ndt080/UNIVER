package configuration.schemes;

import java.util.*;

public class SqlInsertionSchemes {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql-insertion-schemes");

    public static String insertClient = resourceBundle.getString("INSERT_CLIENT");
    public static String insertAdmin = resourceBundle.getString("INSERT_ADMIN");
    public static String insertHorse = resourceBundle.getString("INSERT_HORSE");
    public static String insertRace = resourceBundle.getString("INSERT_RACE");
    public static String insertBet = resourceBundle.getString("INSERT_BET");
}
