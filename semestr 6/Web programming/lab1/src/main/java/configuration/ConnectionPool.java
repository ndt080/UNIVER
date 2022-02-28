package configuration;


import configuration.settings.*;
import exceptions.*;
import org.apache.logging.log4j.*;

import java.sql.*;
import java.util.concurrent.*;

/**
 * Connection pool class
 */
public class ConnectionPool {
    /**
     * Logger object
     */
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    /**
     * Struct for storage connections
     */
    static LinkedBlockingDeque<Connection> connections;
    /**
     * Connection poll instance
     */
    private static ConnectionPool connectionPool;

    /**
     * Constructor of connection poll object
     * @param connectionCount connections count
     */
    private ConnectionPool(int connectionCount) {
        try {
            Class.forName(DatabaseSettings.DRIVER);
            connections = new LinkedBlockingDeque<Connection>();

            for (int i = 0; i < connectionCount; i++) {
                connections.add(createConnection());
            }
        } catch (ClassNotFoundException | ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get current connection pool instance. Create poll if not exists
     * @return Connection poll instance
     */
    public static ConnectionPool getConnectionPool() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(5);
        }
        return connectionPool;
    }

    /**
     * Close connections of poll
     * @throws ConnectionPoolException
     */
    public static void closeConnections() throws ConnectionPoolException {
        try {
            logger.info("Closing connections...");
            int size = connections.size();

            for (int i = 0; i < size; i++) {
                connections.getFirst().close();
                connections.pollFirst();
            }
            logger.info("Connections closed.");
        } catch (SQLException e) {
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    /**
     * Create database connection
     * @return database connections
     * @throws ConnectionPoolException
     */
    private Connection createConnection() throws ConnectionPoolException {
        try {
            Class.forName(DatabaseSettings.DRIVER);
            logger.info("Connection created");

            return DriverManager.getConnection(DatabaseSettings.URL, DatabaseSettings.USERNAME, DatabaseSettings.PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    /**
     * Get database connection
     * @return database connection
     * @throws ConnectionPoolException
     */
    public synchronized Connection getConnection() throws ConnectionPoolException {
        try {
            logger.info("Connection was peeked");
            return connections.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    /**
     * Free database connection
     * @param connection database connection
     */
    public synchronized void freeConnection(Connection connection) {
        if (connection != null) {
            logger.info("Connection was released");
            connections.add(connection);
        }
    }
}
