package daos;

import configuration.ConnectionPool;
import configuration.schemes.SqlCreationSchemes;
import configuration.schemes.SqlInsertionSchemes;
import configuration.schemes.SqlRemovingSchemes;
import configuration.schemes.SqlSelectionSchemes;
import entities.*;
import exceptions.ConnectionPoolException;
import exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.MapperUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BetDAO implements DAO<Bet> {
    private static final Logger logger = LogManager.getLogger(BetDAO.class);
    ConnectionPool connectionPool;

    public BetDAO() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public void createTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlCreationSchemes.createBetTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Bet table was created");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void dropTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlRemovingSchemes.dropBetTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Bet table was removed");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Bet> get(int id) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlSelectionSchemes.selectBetById);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            Bet bet = null;
            while (result.next()) {
                bet = MapperUtil.mapRowToBet(result);
            }

            connectionPool.freeConnection(connection);
            logger.info("Bet was selected");
            return Optional.ofNullable(bet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bet> getAll() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            ResultSet result = connection.prepareStatement(SqlSelectionSchemes.selectAllBets).executeQuery();

            List<Bet> bets = new ArrayList<Bet>();
            while (result.next()) {
                bets.add(MapperUtil.mapRowToBet(result));
            }

            connectionPool.freeConnection(connection);
            logger.info("Bets was selected");
            return bets;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(Bet bet) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlInsertionSchemes.insertBet);
            statement.setInt(1, bet.getClient().getId());
            statement.setInt(2, bet.getRace().getId());
            statement.setInt(3, bet.getHorse().getId());
            statement.setDouble(4, bet.getAmount());
            statement.setDouble(5, bet.getCoefficient());
            statement.setTimestamp(6, new Timestamp(bet.getCreatedAt().getTime()));
            statement.setInt(7, bet.getCreator().getId());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Bet was inserted");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Bet bet, String[] params) throws DAOException {}

    @Override
    public void delete(Bet bet) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlRemovingSchemes.removeBetById);
            statement.setInt(1, bet.getId());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Bet with ID:" + bet.getId() + " was removed");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
