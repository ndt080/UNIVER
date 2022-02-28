package entities;

/**
 * Client class
 */
public class Client extends User {
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
     * Get client object as string
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Client: { id: %d, lastname: %s, name: %s };", this.id, this.lastname, this.name);
    }
}
