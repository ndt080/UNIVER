package mypack;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Designer implements Serializable{
    public int ConstSum = 97;  //цена за метр
    private Date Ddt;
    public Date GetDate(){
        return this.Ddt;
    }

    public ArrayList<Structure> GetStructure(){
        return this.Table;
    }

    ArrayList<Structure> Table = new ArrayList<>();

    void CalcPrice(Client client){
        Order order = client.GetOrder();
        double newPrice = Calc(order.GetSquare(), order.GetFloors());
        int size = Table.size();
        for (int i = 0; i < size; i++){
            if(Table.get(i).Personid == client.GetID()){
                Table.get(i).Price = newPrice;
                return;
            }
        }
        Table.add(new Structure(client.GetID(), newPrice, order));
    }

    double Calc(double square, int floors){
        double price = square * this.ConstSum*floors;
        return price;
    }


    public Designer(){
        this.Ddt = new Date();
    }

    @Override
    public String toString(){
        String tabl = "";
        for(int i = 0; i < Table.size(); i++){
            tabl += "\n" + Table.get(i).toString();
        }
        return "Designer, " + Ddt + ":" + tabl;
    }
}

class Structure implements Serializable{
    int Personid;
    double Price;
    Order Order;

    public Structure(int personid, double price, Order order){
        this.Price = price;
        this.Personid = personid;
        this.Order = order;
    }

    @Override
    public String toString(){
        return '{' +
                "Personid=" + Personid +
                ", Price=" + Price +
                ", Order=" + Order +
                '}';
    }
}