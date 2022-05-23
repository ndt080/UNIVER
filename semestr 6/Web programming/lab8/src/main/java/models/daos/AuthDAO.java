package models.daos;

import configuration.JPAManager;
import exceptions.DAOException;
import models.entities.Auth;
import models.entities.Auth_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * AuthDAO class
 */
public class AuthDAO {
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(AuthDAO.class);

    /**
     * SignIn method
     *
     * @param login    user login
     * @param password user password
     * @return authorization data
     * @throws DAOException error
     */
    public Auth signIn(String login, String password) throws DAOException {
        EntityManager entityManager = null;
        String userType = null;

        try {
            entityManager = JPAManager.getEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Auth> cq = cb.createQuery(Auth.class);
            Root<Auth> root = cq.from(Auth.class);
            cq.where(cb.equal(root.get(Auth_.login), login), cb.equal(root.get(Auth_.password), password));

            return entityManager.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    /**
     * Authorization handler
     *
     * @param login user login
     * @return is auth
     * @throws DAOException error
     */
    public boolean authHandler(String login) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;

        try {
            entityManager = JPAManager.getEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();


            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Auth> auth = cq.from(Auth.class);

            cq.select(cb.count(auth));
            cq.where(cb.equal(auth.get(Auth_.login), login));

            TypedQuery<Long> query = entityManager.createQuery(cq);

            boolean result = query.getSingleResult() > 0;
            entityTransaction.commit();

            logger.info("Result was found");
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    /**
     * SignUp method
     *
     * @param user auth data
     * @throws DAOException error
     */
    public void signUp(Auth user) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            entityManager = JPAManager.getEntityManager();
            transaction = entityManager.getTransaction();

            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();

            logger.info(e.getMessage());
            logger.info("Failed while inserting");

            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }
}
