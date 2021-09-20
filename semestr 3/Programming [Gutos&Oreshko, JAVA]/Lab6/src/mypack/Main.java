package mypack;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args){
        Locale loc;
        String lang = "en";
        String country = "GB";
        if ( args.length == 2 ){
            lang = args[0];
            country = args[1];
        }
        loc = new Locale( lang, country );

        ResourceBundle rb = ResourceBundle.getBundle("Msg", loc);

        Order o1 = new Order(55,3);
        Client Bob = new Client(3425, o1);

        Order o2 = new Order(77.5,2);
        Client Tom = new Client(14, o2);

        Order o3 = new Order(120,4);
        Client Jack = new Client(376988, o3);

        Client[] Clt = {Bob, Tom, Jack};

        Designer designer = new Designer();
        designer.CalcPrice(Bob);
        designer.CalcPrice(Tom);

        //System.out.println(designer);

/*        try{
            designer.AddBalance(Alex, 200);
            designer.ChangeService(Alex.GetID(), 5);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }*/

        if(true){
            try{
                Connector con1 = new Connector("clients.dat");
                con1.writeAb(Clt);
                Client[] arr = con1.readAb();

                Connector con2 = new Connector("designer.dat");
                con2.writeAd(designer);
                Designer adm = con2.readAd();

                for (Client a : arr){
                    System.out.println(
                            rb.getString("creation") + ": " + a.GetDate() + "\n" +
                            "ID: " + a.GetID() + "\n" +
                            rb.getString("orderbyclient") + ": " + a.GetOrder().toString() + "\n"
                    );
                }
                System.out.println();


                System.out.println(
                        rb.getString("creation") + ": " + adm.GetDate() + "\n"
                );

                for (Structure s : adm.GetStructure()){
                    System.out.println(
                            "ID: " + s.Personid + "\n" +
                            rb.getString("price") + ": " + s.Price + "$ \n" +
                            rb.getString("order") + ": " + s.Order + "\n"
                    );
                }
            }catch (Exception e){
                System.err.println(e);
            }
        }
    }
}
