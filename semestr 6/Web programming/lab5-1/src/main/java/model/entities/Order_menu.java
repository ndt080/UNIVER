package model.entities;


import javax.persistence.*;

@Entity
@Table(name = "order_menu")
public class Order_menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_id")
    private int order_id;

    @Column(name = "menu_id")
    private int menu_id;

    @Column(name = "amount")
    private int amount;

    public Order_menu(){}

    public Order_menu(int id, int order_id, int menu_id, int amount) {
        this.id = id;
        this.order_id = order_id;
        this.menu_id = menu_id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
