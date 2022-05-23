package models.daos;

import configuration.ConnectionPool;
import configuration.JPAManager;
import configuration.schemes.*;
import models.entities.Bet;
import models.entities.Client;
import models.entities.Race;
import exceptions.*;
import org.apache.logging.log4j.*;
import org.hibernate.Session;
import utils.MapperUtil;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class RaceDAO implements DAO<Race> {
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(RaceDAO.class);
    /**
     * Connection pool instance
     */
    ConnectionPool connectionPool;

    public RaceDAO() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public Optional<Race> get(int id) throws DAOException {
        EntityManager entityManager = null;
        Race race = null;
        try {
            entityManager = JPAManager.getEntityManager();
            race = entityManager
                    .createNamedQuery("Race.findById", Race.class)
                    .setParameter("id", id)
                    .getSingleResult();

            logger.info("Client was selected.");
            return Optional.ofNullable(race);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
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
            logger.info("Races was selected: " + races);
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
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.save(race);
            logger.info("Race was inserted");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public void update(Race race, String[] params) throws DAOException {}

    @Override
    public void delete(Race race) throws DAOException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.delete(race);
            logger.info("Race with ID:" + race.getId() + " was removed");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
