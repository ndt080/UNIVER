package entities;

import java.util.Date;
import javax.persistence.*;



/**
 * Bet class
 */
@Entity
@Table(name = "bet")
@NamedQueries({
        @NamedQuery(name = "Bet.findAll", query = "select a from Bet a"),
        @NamedQuery(name = "Bet.findById", query = "select a from Bet a where a.id = :id"),
        @NamedQuery(name = "Bet.deleteById", query = "delete from Bet a where a.id = :id")
})
public class Bet {
    /**
     * Bet id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    /**
     * The client making the bet
     */
    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;

    /**
     * The race they bet on
     */
    @ManyToOne
    @JoinColumn(name = "race_id")
    Race race;

    /**
     * The horse that is being bet on
     */
    @ManyToOne
    @JoinColumn(name = "horse_id")
    Horse horse;

    /**
     * Bid amount
     */
    @Column(name = "amount")
    double amount;

    /**
     * Bid coefficient
     */
    @Column(name = "coefficient")
    double coefficient;

    /**
     * Date of registration of the bid
     */
    @Column(name = "created_At")
    Date createdAt;

    /**
     * The administrator who registered the bid
     */
    @ManyToOne
    @JoinColumn(name = "creator")
    Admin creator;

    /**
     * Bet object constructor
     * @param id Bet id
     * @param client The client making the bet
     * @param race The race they bet on
     * @param horse The horse that is being bet on
     * @param amount Bid amount
     * @param coefficient Bid coefficient
     * @param createdAt Date of registration of the bid
     * @param creator The administrator who registered the bid
     */
    public Bet(int id, Client client, Race race, Horse horse, double amount, double coefficient, Date createdAt, Admin creator) {
        this.id = id;
        this.client = client;
        this.race = race;
        this.horse = horse;
        this.amount = amount;
        this.coefficient = coefficient;
        this.createdAt = createdAt;
        this.creator = creator;
    }

    /**
     * Bet object empty constructor
     */
    public Bet() {}

    /**
     * Get Bet id
     * @return Bet id
     */
    public int getId() { return id; }
    /**
     * Set Bet id
     * @param id Bet id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Get The client making the bet
     * @return The client making the bet
     */
    public Client getClient() { return client; }
    /**
     * Set The client making the bet
     * @param client The client making the bet
     */
    public void setClient(Client client) { this.client = client; }

    /**
     * Get The race they bet on
     * @return The race they bet on
     */
    public Race getRace() { return race; }
    /**
     * Set The race they bet on
     * @param race The race they bet on
     */
    public void setRace(Race race) { this.race = race; }

    /**
     * Get horse that is being bet on
     * @return The horse that is being bet on
     */
    public Horse getHorse() { return horse; }
    /**
     * Set horse that is being bet on
     * @param horse The horse that is being bet on
     */
    public void setHorse(Horse horse) { this.horse = horse; }

    /**
     * Get Bid amount
     * @return Bid amount
     */
    public double getAmount() { return amount; }
    /**
     * Bid amount
     * @param amount Bid amount
     */
    public void setAmount(double amount) { this.amount = amount; }

    /**
     * Get Bid coefficient
     * @return  Bid coefficient
     */
    public double getCoefficient() { return coefficient; }
    /**
     * Set  Bid coefficient
     * @param coefficient  Bid coefficient
     */
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }

    /**
     * Get Date of registration of the bid
     * @return Date of registration of the bid
     */
    public Date getCreatedAt() { return createdAt; }
    /**
     * Set Date of registration of the bid
     * @param createdAt Date of registration of the bid
     */
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    /**
     * Get administrator who registered the bid
     * @return The administrator who registered the bid
     */
    public Admin getCreator() { return creator; }

    /**
     * Set The administrator who registered the bid
     * @param creator The administrator who registered the bid
     */
    public void setCreator(Admin creator) { this.creator = creator; }

    /**
     * Get bet object as string
     *
     * @return string
     */
    @Override
    public String toString() {
        return String.format("""
                        Bet: {\s
                        id: %d,\s
                        client: %s,\s
                        race: %s,\s
                        horse: %s,\s
                        amount: %f,\s
                        coefficient: %f,\s
                        createdAt: %s,\s
                        creator: %s\s
                        };""",
                this.id, this.client, this.race, this.horse, this.amount, this.coefficient, this.createdAt, this.creator);
    }
}
