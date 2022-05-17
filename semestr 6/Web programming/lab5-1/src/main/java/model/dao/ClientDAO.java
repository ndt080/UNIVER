package model.dao;

import connection.ConnectionPool;
import connection.JPAUtility;
import model.entities.*;
import exception.DAOException;
import exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO CLASS FOR CLIENT
 */
public class ClientDAO {
    private static final Logger logger = LogManager.getLogger(ClientDAO.class);
    ConnectionPool connectionPool;

    public ClientDAO() {
        this.connectionPool = ConnectionPool.getConnectionPool();
    }

    /**
     * Shows all dishes available
     * @return
     * @throws DAOException
     */
    public List<Dish> showMenu() throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        List<Dish> res ;
        try {
            entityManager = JPAUtility.getEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Dish> query = cb.createQuery(Dish.class);
            Root<Dish> dishRoot = query.from(Dish.class);
            res = entityManager.createQuery(query).getResultList();
            entityTransaction.commit();
            logger.info("All dishes was shown");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        if (res != null && res.size() != 0) {
            return res;
        }
        return null;
    }

    /**
     * Creates a new order
     * @param totalPrice
     * @param id
     * @throws DAOException
     */
    public void makeOrder(float totalPrice, int id) throws DAOException {
        EntityManager entityManager = null;
        Order order = new Order();
        try {
            entityManager = JPAUtility.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            order.setClient_id(id);
            order.setApproved(false);
            order.setPaid(false);
            order.setPrice(totalPrice);
            session.save(order);
            logger.info("Order was Added");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    /**
     * Gets client by his name
     * @param name
     * @return
     * @throws DAOException
     */
    public Client getClientByName(String name) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        Client res = null;
        try {
            entityManager = JPAUtility.getEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Client> query = cb.createQuery(Client.class);
            Root<Client> clientRoot = query.from(Client.class);
            query.where(cb.equal(clientRoot.get(Client_.name), name));
            res = entityManager.createQuery(query).getSingleResult();
            entityTransaction.commit();
            logger.info("Client was found");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return res;
    }

    /**
     * Makes new client
     * @param name
     * @param lastName
     * @param cash
     * @throws DAOException
     */
    public void createClient(String name, String lastName, float cash) throws DAOException {
        EntityManager entityManager = null;
        Client client = new Client();
        try {
            entityManager = JPAUtility.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            client.setName(name);
            client.setLastName(lastName);
            client.setCash(cash);
            session.save(client);
            logger.info("Client was Added");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    /**
     * Approves an orders
     * @param id
     * @throws DAOException
     */
    public void orderApproved(int id) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = JPAUtility.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<Order> updateOrder = cb.createCriteriaUpdate(Order.class);
            Root<Order> orderRoot = updateOrder.from(Order.class);
            updateOrder.set(Order_.isApproved, true);
            updateOrder.where(cb.equal(orderRoot.get(Order_.id), id));
            entityManager.createQuery(updateOrder).executeUpdate();
            transaction.commit();
            logger.info("Order was Approved");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    /**
     * Pays an order
     * @param id
     * @throws DAOException
     */
    public void orderPaid(int id) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = JPAUtility.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<Order> updateOrder = cb.createCriteriaUpdate(Order.class);
            Root<Order> orderRoot = updateOrder.from(Order.class);
            updateOrder.set(Order_.isPaid, true);
            updateOrder.where(cb.equal(orderRoot.get(Order_.id), id));
            entityManager.createQuery(updateOrder).executeUpdate();
            transaction.commit();
            logger.info("Order was Approved");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    /**
     * @param orderID
     * @param SQL_Script
     * @throws DAOException
     */
    public void setOrder(int orderID, String SQL_Script) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_Script);
            statement.setInt(1, orderID);
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Order was Paid");
        } catch (SQLException | PoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Adds dish to order
     * @param order_id
     * @param menu_id
     * @param amount
     * @throws DAOException
     */
    public void addDishToOrder(int order_id, int menu_id, int amount) throws DAOException {
        EntityManager entityManager = null;
        Order_menu dish = new Order_menu();
        try {
            entityManager = JPAUtility.getEntityManager();
            Session session = entityManager.unwrap(Session.class);
            dish.setOrder_id(order_id);
            dish.setMenu_id(menu_id);
            dish.setAmount(amount);
            session.save(dish);
            logger.info("Dish was Added");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

    }

    /**
     * Gets all orders
     * @return
     */
    public List<Order> getAllOrders() throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        List<Order> res;
        try {
            entityManager = JPAUtility.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<Order> query = cb.createQuery(Order.class);
            Root<Order> orderRoot = query.from(Order.class);
            res = entityManager.createQuery(query).getResultList();
            logger.info("All orders were got");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        if (res != null && res.size() != 0) {
            return res;
        }
        return null;
    }


    public List<Client> getAllClients() throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        List<Client> res;
        try {
            entityManager = JPAUtility.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<Client> query = cb.createQuery(Client.class);
            Root<Client> orderRoot = query.from(Client.class);
            res = entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        if (res != null && res.size() != 0) {
            return res;
        }
        return null;
    }
    /**
     * Gets all approved orders
     * @return
     */
    public List<Order> getAllApprovedOrders() throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        List<Order> res;
        try {
            entityManager = JPAUtility.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<Order> query = cb.createQuery(Order.class);
            Root<Order> orderRoot = query.from(Order.class);
            query.where(cb.equal(orderRoot.get(Order_.isApproved), true));
            res = entityManager.createQuery(query).getResultList();
            logger.info("All approved orders were got");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        if (res != null && res.size() != 0) {
            return res;
        }
        return null;
    }

    /**
     * Gets client's orders
     * @param id
     * @return
     */
    public List<Order> getAllClientOrders(int id) throws DAOException {
        EntityManager entityManager = null;
        EntityTransaction transaction ;
        List<Order> res;
        try {
            entityManager = JPAUtility.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<Order> query = cb.createQuery(Order.class);
            Root<Order> orderRoot = query.from(Order.class);
            query.where(cb.equal(orderRoot.get(Order_.client_id), id));
            res = entityManager.createQuery(query).getResultList();
            logger.info("All clients orders were got");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        if (res != null && res.size() != 0) {
            return res;
        }
        return null;
    }
}
