package model.entities;


import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "printMenu", query = "select d from Dish d")
})


@Entity
@Table (name = "menu")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "price")
    private float price;
    @Column (name = "description")
    private String description;

    public Dish(){}
    public Dish(int id, float price, String description) {
        this.id = id;
        this.price = price;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", price=" + price +
                ", description='" + description + '\'' +
                "}\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
