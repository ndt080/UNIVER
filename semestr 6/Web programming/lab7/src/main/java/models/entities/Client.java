package models.entities;

import javax.persistence.*;

/**
 * Client class
 */
@Entity
@Table(name = "client")
@NamedQueries({
        @NamedQuery(name = "Client.findAll", query = "select a from Client a"),
        @NamedQuery(name = "Client.findById", query = "select a from Client a where a.id = :id"),
        @NamedQuery(name = "Client.deleteById", query = "delete from Client a where a.id = :id")
})
public class Client {
    /**
     * User Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Client class constructor
     * @param id client id
     * @param lastname client lastname
     * @param name client name
     */
    public Client(Integer id, String lastname, String name) {
        this.id = id;
        this.lastname = lastname;
        this.name = name;
    }

    /**
     * Client class empty constructor
     */
    public Client() {}

    /**
     * Get client object as string
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Client: { id: %d, lastname: %s, name: %s };", this.id, this.lastname, this.name);
    }
}
