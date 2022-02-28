package daos;

import configuration.ConnectionPool;
import configuration.schemes.*;
import entities.Race;
import exceptions.*;
import org.apache.logging.log4j.*;
import utils.MapperUtil;

import java.sql.*;
import java.util.*;
import java.util.Date;


public class RaceDAO implements DAO<Race> {
    private static final Logger logger = LogManager.getLogger(RaceDAO.class);
    ConnectionPool connectionPool;

    public RaceDAO() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public void createTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlCreationSchemes.createRaceTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Race table was created");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void dropTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlRemovingSchemes.dropRaceTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Race table was removed");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Race> get(int id) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlSelectionSchemes.selectRaceById);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            Race race = null;
            while (result.next()) {
                race = MapperUtil.mapRowToRace(result);
            }

            connectionPool.freeConnection(connection);
            logger.info("Race was selected.");
            return Optional.ofNullable(race);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Race> getAll() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            ResultSet result = connection.prepareStatement(SqlSelectionSchemes.selectAllRaces).executeQuery();

            List<Race> races = new ArrayList<Race>();
            while (result.next()) {
                races.add(MapperUtil.mapRowToRace(result));
            }
            connectionPool.freeConnection(connection);
            logger.info("Races was selected.");
            return races;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public List<Race> getByDate(Date date) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlSelectionSchemes.selectRacesByDate);
            statement.setTimestamp(1, new Timestamp(date.getTime()));
            ResultSet result = statement.executeQuery();

            List<Race> races = new ArrayList<Race>();
            while (result.next()) {
                races.add(MapperUtil.mapRowToRace(result));
            }
            connectionPool.freeConnection(connection);
            logger.info("Races was selected: " + races);
            return races;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(Race race) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlInsertionSchemes.insertRace);
            statement.setTimestamp(1, new Timestamp(race.getDate().getTime()));

            Array array = connection.createArrayOf("INTEGER", race.getParticipantIdsArray());
            statement.setArray(2, array);

            statement.setInt(3, race.getWinner().getId());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Race was inserted: " + race);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Race race, String[] params) throws DAOException {}

    @Override
    public void delete(Race race) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlRemovingSchemes.removeRaceById);
            statement.setInt(1, race.getId());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Race with ID:" + race.getId() + " was removed");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
