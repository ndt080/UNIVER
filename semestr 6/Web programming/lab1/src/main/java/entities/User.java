package entities;

/**
 * User base class
 */
public abstract class User {
    /**
     * User Id
     */
    int id;
    /**
     * User lastname
     */
    String lastname;
    /**
     * User name
     */
    String name;

    /**
     * Get user id
     * @return user id
     */
    public int getId() { return id; }
    /**
     * Set user id
     * @param id user id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Get user lastname
     * @return user lastname
     */
    public String getLastname() { return lastname; }
    /**
     * Set user lastname
     * @param lastname user lastname
     */
    public void setLastname(String lastname) { this.lastname = lastname; }

    /**
     * Get user name
     * @return user name
     */
    public String getName() { return name; }
    /**
     * Set user name
     * @param name user name
     */
    public void setName(String name) { this.name = name; }
}
