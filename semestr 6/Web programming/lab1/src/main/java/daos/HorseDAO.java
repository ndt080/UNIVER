package daos;

import configuration.ConnectionPool;
import configuration.schemes.*;
import entities.Horse;
import exceptions.*;
import org.apache.logging.log4j.*;

import java.sql.*;
import java.util.*;


public class HorseDAO implements DAO<Horse> {
    private static final Logger logger = LogManager.getLogger(HorseDAO.class);
    ConnectionPool connectionPool;

    public HorseDAO() { connectionPool = ConnectionPool.getConnectionPool(); }

    @Override
    public void createTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlCreationSchemes.createHorseTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Horse table was created");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void dropTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlRemovingSchemes.dropHorseTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Horse table was removed");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Horse> get(int id) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlSelectionSchemes.selectHorseById);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            Horse horse = null;
            while (result.next()) {
                horse = new Horse(
                        result.getInt("ID"),
                        result.getString("NAME"),
                        result.getString("RIDER_NAME")
                );
            }

            connectionPool.freeConnection(connection);
            logger.info("Horse was selected.");
            return Optional.ofNullable(horse);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Horse> getAll() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            ResultSet result = connection.prepareStatement(SqlSelectionSchemes.selectAllHorses).executeQuery();

            List<Horse> horses = new ArrayList<Horse>();
            while (result.next()) {
                horses.add(new Horse(
                        result.getInt("ID"),
                        result.getString("NAME"),
                        result.getString("RIDER_NAME")
                ));
            }

            connectionPool.freeConnection(connection);
            logger.info("Horses was selected.");
            return horses;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public List<Horse> getByRaceId(int raceID) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlSelectionSchemes.selectHorsesByRaceId);
            statement.setInt(1, raceID);
            ResultSet result = statement.executeQuery();

            List<Horse> horses = new ArrayList<Horse>();
            while (result.next()) {
                horses.add(new Horse(
                        result.getInt("ID"),
                        result.getString("NAME"),
                        result.getString("RIDER_NAME")
                ));
            }

            connectionPool.freeConnection(connection);
            logger.info("Horses was selected.");
            return horses;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(Horse horse) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlInsertionSchemes.insertHorse);
            statement.setString(1, horse.getName());
            statement.setString(2, horse.getRiderName());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Horse was inserted.");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Horse horse, String[] params) throws DAOException {}

    @Override
    public void delete(Horse horse) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlRemovingSchemes.removeHorseById);
            statement.setInt(1, horse.getId());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Horse with ID:" + horse.getId() + " was removed");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
