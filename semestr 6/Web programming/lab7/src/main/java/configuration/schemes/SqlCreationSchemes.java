package configuration.schemes;

import java.util.ResourceBundle;

/**
 * SQL Data Creation Schemes
 */
public class SqlCreationSchemes {
    /**
     * Recourse bundle
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql-creation-schemes");

    public static String createClientTable = resourceBundle.getString("CREATE_CLIENT_TABLE");
    public static String createAdminTable = resourceBundle.getString("CREATE_ADMIN_TABLE");
    public static String createHorseTable = resourceBundle.getString("CREATE_HORSE_TABLE");
    public static String createRaceTable = resourceBundle.getString("CREATE_RACE_TABLE");
    public static String createBetTable = resourceBundle.getString("CREATE_BET_TABLE");
}
