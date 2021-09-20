package mypack;

import java.io.Serializable;
import java.util.Date;

public class Client implements Serializable{
    private Date Ddt;
    private int ID;
    private Order ClientOrder;

    public int GetID()
    {
        return this.ID;
    }
    public Date GetDate()
    {
        return this.Ddt;
    }
    public Order GetOrder(){
        return this.ClientOrder;
    }

    public Client(int IDClient, Order order){
        this.ID = IDClient;
        this.ClientOrder = order;
        this.Ddt = new Date();
    }

    @Override
    public String toString() {
        return "Client{" +
                "Ddt=" + Ddt +
                ", ID=" + ID +
                ", Order= " + ClientOrder.toString() +
                '}';
    }
}

