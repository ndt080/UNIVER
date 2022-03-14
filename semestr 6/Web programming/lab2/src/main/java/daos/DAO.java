package daos;

import configuration.ConnectionPool;
import configuration.JPAManager;
import exceptions.DAOException;

import java.util.List;
import java.util.Optional;

/**
 * The DAO interface defining the main methods
 * @param <T> DAO type
 */
public interface DAO<T> {
    /**
     * Pool on connection
     */
    ConnectionPool connectionPool = null;

    /**
     * Method of getting an object
     * @param id object id
     * @return object of type T
     */
    Optional<T> get(int id) throws DAOException;

    /**
     * Method of getting an objects
     * @return object list of type T
     * @throws DAOException
     */
    List<T> getAll() throws DAOException;

    /**
     * Object insertion method
     * @param t object of type T
     * @throws DAOException
     */
    void insert(T t) throws DAOException;

    /**
     * Object updating method
     * @param t object of type T
     * @param params other params
     * @throws DAOException
     */
    void update(T t, String[] params) throws DAOException;

    /**
     * Object deleting method
     * @param t object of type T
     * @throws DAOException
     */
    void delete(T t) throws DAOException;
}
