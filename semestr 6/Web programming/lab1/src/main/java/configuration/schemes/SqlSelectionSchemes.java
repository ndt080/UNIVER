package configuration.schemes;

import java.util.ResourceBundle;

/**
 * SQL Data Selection Schemes
 */
public class SqlSelectionSchemes {
    /**
     * Recourse bundle
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql-selection-schemes");

    public static String selectAllClients = resourceBundle.getString("SELECT_ALL_CLIENTS");
    public static String selectClientById = resourceBundle.getString("SELECT_CLIENT_BY_ID");
    public static String selectWinnerClientsByRaceId = resourceBundle.getString("SELECT_WINNER_CLIENT_BY_RACE_ID");

    public static String selectAllAdmins = resourceBundle.getString("SELECT_ALL_ADMINS");
    public static String selectAdminById = resourceBundle.getString("SELECT_ADMIN_BY_ID");

    public static String selectAllHorses = resourceBundle.getString("SELECT_ALL_HORSES");
    public static String selectHorseById = resourceBundle.getString("SELECT_HORSE_BY_ID");
    public static String selectHorsesByRaceId = resourceBundle.getString("SELECT_HORSES_BY_RACE_ID");

    public static String selectAllRaces = resourceBundle.getString("SELECT_ALL_RACES");
    public static String selectRaceById = resourceBundle.getString("SELECT_RACE_BY_ID");
    public static String selectRacesByDate = resourceBundle.getString("SELECT_RACES_BY_DATE");

    public static String selectAllBets = resourceBundle.getString("SELECT_ALL_BETS");
    public static String selectBetById = resourceBundle.getString("SELECT_BET_BY_ID");
}
