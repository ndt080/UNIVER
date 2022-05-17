package model.dao;

import connection.ConnectionPool;
import connection.JPAUtility;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * @param <T>
 */
public abstract class DAO<T> {
    private static final Logger logger = LogManager.getLogger(DAO.class);
    ConnectionPool connectionPool = null;

    JPAUtility connection = null;

    DAO() throws Exception {
        connection = new JPAUtility();
    }


    /**
     * @param id
     * @return
     * @throws DAOException
     */
    abstract T read(int id) throws DAOException;

    /**
     * @param entity
     * @return
     * @throws DAOException
     */
    void create(T entity) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = JPAUtility.getEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
            logger.info(entity.getClass().getName()+"was created");
        } catch (Exception e) {
            logger.info(e.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }


    /**
     * @param entity
     * @throws DAOException
     */
    abstract void update(T entity) throws DAOException;


    /**
     * @param id
     * @throws DAOException
     */
    abstract void delete(int id) throws DAOException;
}
