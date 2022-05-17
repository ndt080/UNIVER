package model.dao;

import model.entities.Dish;
import exception.DAOException;
import exception.PoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO CLASS FOR MENU
 */
public class MenuDAO extends DAO<Dish> {

    private String CREATE_SQL = "INSERT INTO menu (price, description) VALUES (?,?)";

    private String READ_SQL = "SELECT * FROM menu WHERE id =?";

    private String UPDATE_SQL ="UPDATE menu set price=? WHERE id=?";

    private String DELETE_SQL ="DELETE FROM menu WHERE id=?";



    MenuDAO() throws Exception {
        super();
    }

    @Override
    Dish read(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(READ_SQL);
            statement.setInt(1, id);
            ResultSet resultSet= statement.executeQuery();
            Dish dish = null;
            while (resultSet.next()){
                dish = new Dish(resultSet.getInt("id"), resultSet.getFloat("price"), resultSet.getString("description"));
            }
            connectionPool.freeConnection(connection);
            return dish;
        } catch (SQLException | PoolException e) {
            throw new DAOException(e.getMessage(),e);
        }


    }

    @Override
    void create(Dish entity) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_SQL);
            statement.setFloat(1,entity.getPrice());
            statement.setString(2,entity.getDescription());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            ResultSet resultSet= statement.executeQuery();
        } catch (SQLException | PoolException e) {
            throw new DAOException(e.getMessage(),e);
        }
    }

    @Override
    void update(Dish entity) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setFloat(1,entity.getPrice());
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            ResultSet resultSet= statement.executeQuery();
        } catch (SQLException | PoolException e) {
            throw new DAOException(e.getMessage(),e);
        }

    }

    @Override
    void delete(int id) throws DAOException {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
            statement.setInt(1,id);
            statement.executeUpdate();
            connectionPool.freeConnection(connection);
            ResultSet resultSet= statement.executeQuery();
        } catch (SQLException | PoolException e) {
            throw new DAOException(e.getMessage(),e);
        }
    }
}
