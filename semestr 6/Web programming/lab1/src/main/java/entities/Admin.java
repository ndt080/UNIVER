package entities;

/**
 * Admin class
 */
public class Admin extends User {
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
     * Get admin object as string
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Admin: { id: %d, lastname: %s, name: %s };", this.id, this.lastname, this.name);
    }
}
