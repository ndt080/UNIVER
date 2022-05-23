package models.daos;

import configuration.ConnectionPool;
import configuration.JPAManager;
import configuration.schemes.*;
import models.entities.Client;
import models.entities.Horse;
import exceptions.*;
import org.apache.logging.log4j.*;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.*;


public class HorseDAO implements DAO<Horse> {
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(HorseDAO.class);
    /**
     * Connection pool instance
     */
    ConnectionPool connectionPool;

    public HorseDAO() { connectionPool = ConnectionPool.getConnectionPool(); }

    @Override
    public Optional<Horse> get(int id) throws DAOException {
        EntityManager entityManager = null;
        List<Horse> res = new ArrayList<>();
        try {
            entityManager = JPAManager.getEntityManager();
            res = entityManager
                    .createNamedQuery("Horse.findById", Horse.class)
                    .setParameter("id", id)
                    .getResultList();

            logger.info("Horse was selected.");
            return Optional.ofNullable(res.get(0));
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public List<Horse> getAll() throws DAOException {
        EntityManager entityManager = null;
        List<Horse> res = new ArrayList<>();
        try {
            entityManager = JPAManager.getEntityManager();
            res = entityManager.createNamedQuery("Horse.findAll", Horse.class).getResultList();
            logger.info("Horse was selected.");
            return res;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
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
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.save(horse);
            logger.info("Horse was inserted");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public void update(Horse horse, String[] params) throws DAOException {}

    @Override
    public void delete(Horse horse) throws DAOException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.delete(horse);
            logger.info("Horse with ID:" + horse.getId() + " was removed");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
