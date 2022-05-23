package models.daos;

import configuration.ConnectionPool;
import configuration.JPAManager;
import exceptions.*;
import models.entities.*;
import org.apache.logging.log4j.*;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class ClientDAO implements DAO<Client> {
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(ClientDAO.class);
    /**
     * Connection pool instance
     */
    ConnectionPool connectionPool;

    public ClientDAO() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public Optional<Client> get(int id) throws DAOException {
        EntityManager entityManager = null;
        Client client = null;
        try {
            entityManager = JPAManager.getEntityManager();
            client = entityManager
                    .createNamedQuery("Client.findById", Client.class)
                    .setParameter("id", id)
                    .getSingleResult();

            logger.info("Client was selected.");
            return Optional.ofNullable(client);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Client> getWinnerClientsByRaceId(int raceId) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        List<Client> clients = new ArrayList<Client>();

        try {
            entityManager = JPAManager.getEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Bet> query = cb.createQuery(Bet.class);

            Root<Bet> clientRoot = query.from(Bet.class);
            Join<Bet, Race> race = clientRoot.join(Bet_.race);

            Predicate winnerPredicate = cb.equal(clientRoot.get(Bet_.horse), race.get(Race_.winner));
            Predicate racePredicate = cb.equal(race.get(Race_.id), raceId);

            Predicate finalPredicate = cb.and(winnerPredicate, racePredicate);
            query.where(finalPredicate);

            clients = entityManager.createQuery(query).getResultList().stream().map(Bet::getClient).collect(Collectors.toList());
            entityTransaction.commit();
            logger.info("Clients was found");
            return clients;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) { entityManager.close(); }
        }
    }

    @Override
    public List<Client> getAll() throws DAOException {
        EntityManager entityManager = null;
        List<Client> res = new ArrayList<>();
        try {
            entityManager = JPAManager.getEntityManager();
            res = entityManager.createNamedQuery("Client.findAll", Client.class).getResultList();
            logger.info("Client was selected.");
            return res;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public void insert(Client client) throws DAOException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.save(client);
            logger.info("Client was inserted");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public void update(Client client, String[] params) throws DAOException {}

    @Override
    public void delete(Client client) throws DAOException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.delete(client);
            logger.info("Client with ID:" + client.getId() + " was removed");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
