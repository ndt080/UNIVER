package configuration.schemes;

import java.util.ResourceBundle;

public class SqlRemovingSchemes {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql-removing-schemes");

    public static String dropClientTable = resourceBundle.getString("DROP_CLIENT_TABLE");
    public static String dropAdminTable = resourceBundle.getString("DROP_ADMIN_TABLE");
    public static String dropHorseTable = resourceBundle.getString("DROP_HORSE_TABLE");
    public static String dropRaceTable = resourceBundle.getString("DROP_RACE_TABLE");
    public static String dropBetTable = resourceBundle.getString("DROP_BET_TABLE");

    public static String removeClientById = resourceBundle.getString("DELETE_CLIENT_BY_ID");
    public static String removeAdminById = resourceBundle.getString("DELETE_ADMIN_BY_ID");
    public static String removeHorseById = resourceBundle.getString("DELETE_HORSE_BY_ID");
    public static String removeRaceById = resourceBundle.getString("DELETE_RACE_BY_ID");
    public static String removeBetById = resourceBundle.getString("DELETE_BET_BY_ID");
}
