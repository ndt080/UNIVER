package tourism;

import java.io.*;
import java.util.*;

public class Tourism implements Serializable {
    // class release version:
    private static final long serialVersionUID = 1L;
    // areas with prompts:
    String dateRace;
    static final String dR = "Дата рейса:       ";
    String timeRace;
    static final String tR = "Время рейса:      ";
    String placeComing;
    static final String pC = "Пункт назначения: ";
    String  namePassenger;
    static final String nP = "Имя пассажира:    ";
    String numberRace;
    static final String nR = "Номер рейса:      ";
    String bagadgeNumbers;
    static final String bN = "К-во мест багажа: ";
    String bagadgeWeight;
    static final String bW = "Вес багажа:       ";

    // validation methods:
    static Boolean validISBN( String str ) {
        int i = 0, ndig = 0;
        for ( ; i < str.length(); i++ ) {
            char ch = str.charAt(i);
            if ( Character.isDigit(ch) ) {
                ndig++;
                continue;
            }
            if ( ch != '-' ) {
                return false;
            }
        }
        return (ndig == 13 || ndig == 10 );
    }

    private static final GregorianCalendar curCalendar = new GregorianCalendar();
    static Boolean validYear( int year ) {
        return year > 0 && year <= curCalendar.get( Calendar.YEAR );
    }

    public static final String authorDel = ",";

    public static Tourism read( String[] data ) throws IOException {
        Tourism obj = new Tourism();
        obj.dateRace = data[0];
        obj.timeRace = data[1];
        obj.placeComing = data[2];
        obj.namePassenger = data[3];
        obj.numberRace= data[4];
        obj.bagadgeNumbers= data[5];
        obj.bagadgeWeight= data[6];
        return obj;
    }

    public Tourism() {
    }

    public static final String areaDel = "\n";

    public String toString() {
        return  dR + dateRace + areaDel +
                tR + timeRace + areaDel +
                pC + placeComing + areaDel +
                nP + namePassenger + areaDel +
                nR + numberRace + areaDel +
                bN + bagadgeNumbers + areaDel +
                bW + bagadgeWeight + areaDel;
    }
}
