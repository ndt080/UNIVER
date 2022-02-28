package daos;

import configuration.ConnectionPool;
import configuration.schemes.*;
import entities.Client;
import exceptions.*;
import org.apache.logging.log4j.*;
import utils.MapperUtil;

import java.sql.*;
import java.util.*;


public class ClientDAO implements DAO<Client> {
    private static final Logger logger = LogManager.getLogger(ClientDAO.class);
    ConnectionPool connectionPool;

    public ClientDAO() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public void createTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlCreationSchemes.createClientTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Client table was created");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void dropTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlRemovingSchemes.dropClientTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Client table was removed");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Client> get(int id) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlSelectionSchemes.selectClientById);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            Client client = null;
            while (result.next()) {
                client = MapperUtil.mapRowToClient(result);
            }

            connectionPool.freeConnection(connection);
            logger.info("Client was selected");
            return Optional.ofNullable(client);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public List<Client> getWinnerClientsByRaceId(int raceId) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlSelectionSchemes.selectWinnerClientsByRaceId);
            statement.setInt(1, raceId);
            ResultSet result = statement.executeQuery();

            List<Client> clients = new ArrayList<Client>();
            while (result.next()) {
                clients.add(MapperUtil.mapRowToClient(result));
            }

            connectionPool.freeConnection(connection);
            logger.info("Clients was selected");
            return clients;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Client> getAll() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            ResultSet result = connection.prepareStatement(SqlSelectionSchemes.selectAllClients).executeQuery();

            List<Client> clients = new ArrayList<Client>();
            while (result.next()) {
                clients.add(MapperUtil.mapRowToClient(result));
            }

            connectionPool.freeConnection(connection);
            logger.info("Clients was selected");
            return clients;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(Client client) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlInsertionSchemes.insertClient);
            statement.setString(1, client.getName());
            statement.setString(2, client.getLastname());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Client was inserted");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Client client, String[] params) throws DAOException {}

    @Override
    public void delete(Client client) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlRemovingSchemes.removeClientById);
            statement.setInt(1, client.getId());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Client with ID:" + client.getId() + " was removed");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
