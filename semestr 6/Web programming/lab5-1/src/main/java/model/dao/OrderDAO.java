package model.dao;

import connection.ConnectionPool;
import connection.JPAUtility;
import model.entities.Order;
import exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {
    private static final Logger logger = LogManager.getLogger(ClientDAO.class);
    ConnectionPool connectionPool;
    private String GetTotalPriceByOrderId = "SELECT sum(price * amount) from order_menu inner join menu on menu.id = order_menu.menu_id where order_id = ?";

    public OrderDAO() {
        this.connectionPool = ConnectionPool.getConnectionPool();
    }

    public void SetTotalPrice(int id) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = JPAUtility.getEntityManager();
            transaction = entityManager.getTransaction();
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GetTotalPriceByOrderId);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            float totalPrice = 0;
            while (resultSet.next()) {
                totalPrice = resultSet.getFloat("sum");
            }
            Order order = entityManager.find(Order.class, id);
            transaction.begin();
            order.setPrice(totalPrice);
            transaction.commit();
            connectionPool.freeConnection(connection);
            logger.info("Total price was set");
        } catch (SQLException | PoolException e) {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
