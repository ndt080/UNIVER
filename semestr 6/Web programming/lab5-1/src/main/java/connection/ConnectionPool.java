package connection;



import exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    static LinkedBlockingDeque<Connection> connections;
    private static ConnectionPool connectionPool = null;


    /**
     * @param num
     */
    private ConnectionPool(int num){
        try {
            Class.forName("org.postgresql.Driver");
            connections = new LinkedBlockingDeque<Connection>();
            for (int i = 0; i < num; i++){
                connections.add(createConnection());
            }
        } catch (ClassNotFoundException | PoolException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public static ConnectionPool getConnectionPool(){
        if (connectionPool == null){
            connectionPool = new ConnectionPool(10);
        }
        return  connectionPool;
    }

    /**
     * @return
     */
    private Connection createConnection() throws PoolException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        String url = resourceBundle.getString("URL");
        String driver = resourceBundle.getString("DRIVER");
        String user = resourceBundle.getString("USER");
        String password = resourceBundle.getString("PASSWORD");
        try{
            Class.forName(driver);
            logger.info("Connection created");
            return DriverManager.getConnection(url,user,password);

        }
        catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
            throw new PoolException(e.getMessage(), e);
        }
    }

    /**
     * @return
     * @throws InterruptedException
     */
    public synchronized Connection getConnection() throws PoolException {
        try {
            logger.info("Connection was peeked");
            return connections.take();
        } catch (InterruptedException e) {
            throw new PoolException(e.getMessage(), e);
        }
    }

    /**
     * @param connection
     */
    public synchronized void freeConnection(Connection connection){
        if (connection != null){
            logger.info("Connection was released");
            connections.add(connection);
        }
    }

    public static void closeConnections() throws PoolException {
        try {
            int size = connections.size();
            for (int i = 0; i < size; i++) {
                connections.getFirst().close();
                connections.pollFirst();
            }
        } catch (SQLException e) {
            throw new PoolException(e.getMessage(), e);
        }
    }

}
