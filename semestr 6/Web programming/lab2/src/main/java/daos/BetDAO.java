package daos;

import configuration.ConnectionPool;
import configuration.JPAManager;
import entities.*;
import exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BetDAO implements DAO<Bet> {
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(BetDAO.class);
    /**
     * Connection pool instance
     */
    ConnectionPool connectionPool;

    public BetDAO() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public Optional<Bet> get(int id) throws DAOException {
        EntityManager entityManager = null;
        Bet bet = null;
        try {
            entityManager = JPAManager.getEntityManager();
            bet = entityManager
                    .createNamedQuery("Bet.findById", Bet.class)
                    .setParameter("id", id)
                    .getSingleResult();

            logger.info("Bet was selected.");
            return Optional.ofNullable(bet);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public List<Bet> getAll() throws DAOException {
        EntityManager entityManager = null;
        List<Bet> res = new ArrayList<>();
        try {
            entityManager = JPAManager.getEntityManager();
            res = entityManager.createNamedQuery("Bet.findAll", Bet.class).getResultList();
            logger.info("Bet was selected.");
            return res;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public void insert(Bet bet) throws DAOException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.save(bet);
            logger.info("Bet was inserted");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public void update(Bet bet, String[] params) throws DAOException {}

    @Override
    public void delete(Bet bet) throws DAOException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.delete(bet);
            logger.info("Bet with ID:" + bet.getId() + " was removed");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
