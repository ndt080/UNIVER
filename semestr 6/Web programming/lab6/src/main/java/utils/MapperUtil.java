package utils;

import java.sql.*;
import java.sql.SQLException;
import java.util.*;
import java.util.Date;
import models.entities.*;

/**
 * Mapper Utility class
 */
public class MapperUtil {
    /**
     * Map SQL table data to entity Bet model
     * @param result  SQL table data
     * @return bet model object
     * @throws SQLException
     */
    public static Bet mapRowToBet(ResultSet result) throws SQLException {
        return new Bet(
            result.getInt("BET_ID"),
            new Client(
                result.getInt("CLIENT_ID"),
                result.getString("CLIENT_LASTNAME"),
                result.getString("CLIENT_NAME")
            ),
            new Race(
                result.getInt("RACE_ID"),
                new Date(result.getTimestamp("RACE_DATE").getTime()),
                List.of((Integer[]) result.getArray("RACE_PARTICIPANT_IDS").getArray()),
                mapRowsToParticipants(result),
                new Horse(
                        result.getInt("RACE_WINNER_ID"),
                        result.getString("RACE_WINNER_NAME"),
                        result.getString("RACE_WINNER_RIDER_NAME")
                )
            ),
            new Horse(
                result.getInt("HORSE_ID"),
                result.getString("HORSE_NAME"),
                result.getString("HORSE_RIDER_NAME")
            ),
            result.getDouble("BET_AMOUNT"),
            result.getDouble("BET_COEFFICIENT"),
            new Date(result.getTimestamp("BET_CREATED_AT").getTime()),
            new Admin(
                result.getInt("CREATOR_ID"),
                result.getString("CREATOR_LASTNAME"),
                result.getString("CREATOR_NAME")
            )
        );
    }

    /**
     * Map SQL table data to entity Client model
     * @param result  SQL table data
     * @return client model object
     * @throws SQLException
     */
    public static Client mapRowToClient(ResultSet result) throws SQLException {
        return new Client(
            result.getInt("ID"),
            result.getString("LASTNAME"),
            result.getString("NAME")
        );
    }

    /**
     * Map SQL table data to entity Race model
     * @param result  SQL table data
     * @return race model object
     * @throws SQLException
     */
    public static Race mapRowToRace(ResultSet result) throws SQLException {
        Race race = new Race(
            result.getInt("RACE_ID"),
            new Date(result.getTimestamp("RACE_DATE").getTime()),
            List.of((Integer[]) result.getArray("RACE_PARTICIPANT_IDS").getArray()),
            result.getInt("WINNER_ID") != 0
            ? new Horse(
                result.getInt("WINNER_ID"),
                result.getString("WINNER_NAME"),
                result.getString("WINNER_RIDER_NAME")
              )
            : null
        );
        race.setParticipants(mapRowsToParticipants(result));
        return race;
    }

    /**
     * Map SQL table data to Participants of entity Race model
     * @param result  SQL table data
     * @return Participants of entity Race model
     * @throws SQLException
     */
    public static List<Horse> mapRowsToParticipants(ResultSet result) throws SQLException {
        List<Horse> participants = new ArrayList<Horse>();
        for (String row: (String[]) result.getArray("RACE_PARTICIPANTS").getArray()) {
            String[] cols = row.split(",");
            participants.add(new Horse(Integer.parseInt(cols[0]), cols[1], cols[2]));
        }

        return participants;
    }
}
