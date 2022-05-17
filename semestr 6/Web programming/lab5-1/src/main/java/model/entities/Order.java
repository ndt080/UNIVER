package model.entities;


import javax.persistence.*;
@NamedQueries({
        @NamedQuery(name = "approveOrder", query = "update Order o set o.isApproved=true where o.id=:id"),
        @NamedQuery(name = "payOrder", query = "update Order o set o.isPaid=true where o.id=:id"),
        @NamedQuery(name = "getAllOrders", query = "SELECT o from Order o"),
        @NamedQuery(name = "getAllClientOrders",query = "select o from  Order o where o.client_id=:id"),
        @NamedQuery(name = "getAllApprovedOrders",query = "select o from  Order o where o.isApproved=true ")

})

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "price")
    private float price;
    @Column(name = "client_id")
    private int client_id;
    @Column(name = "isPaid")
    private boolean isPaid;
    @Column(name = "isApproved")
    private boolean isApproved;

    public Order(){}
    public Order(int id, float price, int client_id, boolean isPaid, boolean isApproved) {
        this.id = id;
        this.price = price;
        this.client_id = client_id;
        this.isPaid = isPaid;
        this.isApproved = isApproved;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price + "$" +
                ", client_id=" + client_id +
                ", isPaid=" + isPaid +
                ", isApproved=" + isApproved +
                '}' +'\n';
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

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
