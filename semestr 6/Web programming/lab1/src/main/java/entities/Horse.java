package entities;

/**
 * Horse class
 */
public class Horse {
    /**
     * Unique horse ID
     */
    int id;
    /**
     * Horse name
     */
    String name;
    /**
     * Name of the horse rider
     */
    String riderName;

    /**
     * Horse object constructor
     * @param id Unique horse ID
     * @param name Horse name
     * @param riderName Name of the horse rider
     */
    public Horse(int id, String name, String riderName) {
        this.id = id;
        this.name = name;
        this.riderName = riderName;
    }

    /**
     * Get unique horse ID
     * @return unique horse ID
     */
    public int getId() { return id; }
    /**
     * Set unique horse ID
     * @param id unique horse ID
     */
    public void setId(int id) { this.id = id; }

    /**
     * Get Horse name
     * @return Horse name
     */
    public String getName() { return name; }
    /**
     * Set Horse name
     * @param name Horse name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Get Name of the horse rider
     * @return Name of the horse rider
     */
    public String getRiderName() { return riderName; }
    /**
     * Name of the horse rider
     * @param riderName Name of the horse rider
     */
    public void setRiderName(String riderName) { this.riderName = riderName; }

    /**
     * Get horse object as string
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Horse: { id: %d, name: %s, riderName: %s };", this.id, this.name, this.riderName);
    }
}
