package daos;

import configuration.ConnectionPool;
import configuration.schemes.*;
import entities.Admin;
import exceptions.*;
import org.apache.logging.log4j.*;

import java.sql.*;
import java.util.*;

public class AdminDAO implements DAO<Admin> {
    private static final Logger logger = LogManager.getLogger(AdminDAO.class);
    ConnectionPool connectionPool;

    public AdminDAO() { connectionPool = ConnectionPool.getConnectionPool(); }

    @Override
    public void createTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlCreationSchemes.createAdminTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Admin table was created");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void dropTable() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            connection.prepareStatement(SqlRemovingSchemes.dropAdminTable).execute();
            connectionPool.freeConnection(connection);
            logger.info("Admin table was removed");
        } catch (SQLException | ConnectionPoolException e) {
            logger.info(e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Admin> get(int id) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlSelectionSchemes.selectAdminById);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            Admin admin = null;
            while (result.next()) {
                admin = new Admin(
                        result.getInt("ID"),
                        result.getString("LASTNAME"),
                        result.getString("NAME")
                );
            }

            connectionPool.freeConnection(connection);
            logger.info("Admin was selected.");
            return Optional.ofNullable(admin);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Admin> getAll() throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            ResultSet result = connection.prepareStatement(SqlSelectionSchemes.selectAllAdmins).executeQuery();

            List<Admin> admins = new ArrayList<Admin>();
            while (result.next()) {
                admins.add(new Admin(
                        result.getInt("ID"),
                        result.getString("LASTNAME"),
                        result.getString("NAME")
                ));
            }

            connectionPool.freeConnection(connection);
            logger.info("Admins was selected.");
            return admins;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(Admin admin) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlInsertionSchemes.insertAdmin);
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getLastname());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Admin was inserted.");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Admin admin, String[] params) throws DAOException {}

    @Override
    public void delete(Admin admin) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlRemovingSchemes.removeAdminById);
            statement.setInt(1, admin.getId());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            logger.info("Admin with ID:" + admin.getId() + " was removed");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
