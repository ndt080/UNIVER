package daos;

import configuration.ConnectionPool;
import configuration.JPAManager;
import entities.Admin;
import exceptions.*;
import org.apache.logging.log4j.*;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.*;


/**
 * Admin DAO class
 */
public class AdminDAO implements DAO<Admin> {
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(AdminDAO.class);
    /**
     * Connection pool instance
     */
    ConnectionPool connectionPool;

    /**
     * Admin DAO object constructor
     */
    public AdminDAO() { connectionPool = ConnectionPool.getConnectionPool(); }

    @Override
    public Optional<Admin> get(int id) throws DAOException {
        EntityManager entityManager = null;
        Admin admin = null;
        try {
            entityManager = JPAManager.getEntityManager();
            admin = entityManager
                    .createNamedQuery("Admin.findById", Admin.class)
                    .setParameter("id", id)
                    .getSingleResult();

            logger.info("Admin was selected.");
            return Optional.ofNullable(admin);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public List<Admin> getAll() throws DAOException {
        EntityManager entityManager = null;
        List<Admin> res = new ArrayList<>();
        try {
            entityManager = JPAManager.getEntityManager();
            res = entityManager.createNamedQuery("Admin.findAll", Admin.class).getResultList();
            logger.info("Admin was selected.");
            return res;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public void insert(Admin admin) throws DAOException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.save(admin);
            logger.info("Admin was inserted");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    @Override
    public void update(Admin admin, String[] params) throws DAOException {}

    @Override
    public void delete(Admin admin) throws DAOException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAManager.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            session.delete(admin);
            logger.info("Admin with ID:" + admin.getId() + " was removed");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
