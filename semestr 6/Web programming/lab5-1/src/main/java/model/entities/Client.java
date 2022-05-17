package model.entities;

import javax.persistence.*;


@NamedQueries({
        @NamedQuery(name ="getClientByName", query = "select c FROM Client c where c.name= :name")
})


@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "cash")
    private float cash;

    public Client(){}

    public Client(int id, String name, String lastName, float cash) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.cash = cash;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cash=" + cash +
                '}' + '\n';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }
}
