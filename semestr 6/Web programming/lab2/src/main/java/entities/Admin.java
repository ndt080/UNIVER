package entities;

import javax.persistence.*;

/**
 * Admin class
 */
@Entity
@Table(name = "admin")
@NamedQueries({
        @NamedQuery(name = "Admin.findAll", query = "select a from Admin a"),
        @NamedQuery(name = "Admin.findById", query = "select a from Admin a where a.id = :id"),
        @NamedQuery(name = "Admin.deleteById", query = "delete from Admin a where a.id = :id")
})
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * User Id
     */
            int id;
    /**
     * User lastname
     */
    @Column(name = "lastname")
    String lastname;
    /**
     * User name
     */
    @Column(name = "name")
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

    /**
     * Admin class constructor
     * @param id admin id
     * @param lastname admin lastname
     * @param name admin name
     */
    public Admin(int id, String lastname, String name) {
        this.id = id;
        this.lastname = lastname;
        this.name = name;
    }

    /**
     * Admin class empty constructor
     */
    public Admin() {}

    /**
     * Get admin object as string
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Admin: { id: %d, lastname: %s, name: %s };", this.id, this.lastname, this.name);
    }
}
