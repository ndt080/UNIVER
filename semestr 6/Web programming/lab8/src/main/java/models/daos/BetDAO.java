package models.daos;

import configuration.JPAManager;
import exceptions.DAOException;
import models.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.Date;


public class BetDAO implements DAO<Bet> {
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(BetDAO.class);

    /**
     * Get bet by id
     * @param id object id
     * @return bet object
     * @throws DAOException error
     */
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

    public List<Bet> getByClientAndDate(String clientId, Date date) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        List<Bet> bets = new ArrayList<Bet>();

        try {
            entityManager = JPAManager.getEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Bet> query = cb.createQuery(Bet.class);

            Root<Bet> clientRoot = query.from(Bet.class);
            Join<Bet, Client> client = clientRoot.join(Bet_.client);

            Predicate datePredicate = cb.equal(cb.function("date", Date.class,
                    clientRoot.get(Bet_.createdAt)), date);
            Predicate clientPredicate = cb.equal(client.get(Client_.id), clientId);

            Predicate finalPredicate = cb.and(datePredicate, clientPredicate);
            query.where(finalPredicate);

            bets = new ArrayList<>(entityManager.createQuery(query).getResultList());
            entityTransaction.commit();
            logger.info("Bets was selected");
            logger.info(bets);
            return bets;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) { entityManager.close(); }
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
