package tourism;

import java.io.Serializable;
import java.util.Scanner;

public class Tourism implements Serializable {

    private String dateRace = null;
    private String timeRace = null;
    private String placeComing = null;
    private String namePassenger = null;
    private int numberRace = 0;
    private int bagadgeNumbers = 0;
    private int bagadgeWeight = 0;


    public static Tourism read(Scanner fin ) {
        Tourism aviaRace = new Tourism();
       // if ( ! fin.hasNextLine()) return null;
        aviaRace.dateRace = fin.nextLine();
        //if ( ! fin.hasNextLine()) return null;
        aviaRace.timeRace = fin.nextLine();
        //if ( ! fin.hasNextLine()) return null;
        aviaRace.placeComing = fin.nextLine();
        //if ( ! fin.hasNextLine()) return null;
        aviaRace.namePassenger = fin.nextLine();
       // if ( ! fin.hasNextLine()) return null;
        aviaRace.numberRace = Integer.parseInt(fin.nextLine());
        aviaRace.bagadgeNumbers = Integer.parseInt(fin.nextLine());
       // if ( ! fin.hasNextLine()) return null;
        aviaRace.bagadgeWeight = Integer.parseInt(fin.nextLine());
        return aviaRace;
    }

    Tourism(){}

    public String toString() {
        return " Номер рейса: " + numberRace     + "|" +
                "Дата вылета : " + dateRace + "|" +
                "Время вылета: " + timeRace + "|" +
                "Пункт Назначения: " + placeComing + "|" +
                "Имя пассажира : " + namePassenger + "|" +
                "Количество мест багажа : " + bagadgeNumbers + "|" +
                "Вес багажа : " + bagadgeWeight ;
    }
}
