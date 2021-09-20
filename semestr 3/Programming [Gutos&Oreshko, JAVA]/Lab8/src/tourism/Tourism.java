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

    public static Boolean nextRead( Scanner fin, PrintStream out ) {
        return nextRead( dR, fin, out );
    }

    static Boolean nextRead( final String prompt, Scanner fin, PrintStream out ) {
        out.print( prompt );
        out.print( ": " );
        return fin.hasNextLine();
    }

    public static final String authorDel = ",";

    public static Tourism read(Scanner fin, PrintStream out )
            throws IOException {
        String str;
        Tourism obj = new Tourism();
        obj.dateRace = fin.nextLine();
        if ( ! nextRead( tR, fin, out ))           return null;
        obj.timeRace = fin.nextLine();
        if ( ! nextRead( pC, fin, out ))             return null;
        obj.placeComing = fin.nextLine();
        if ( ! nextRead( nP, fin, out ))             return null;
        obj.namePassenger = fin.nextLine();
        if ( ! nextRead( nR, fin, out ))       return null;
        obj.numberRace= fin.nextLine();
        if ( ! nextRead( bN, fin, out ))       return null;
        obj.bagadgeNumbers= fin.nextLine();
        if ( ! nextRead( bW, fin, out ))       return null;
        obj.bagadgeWeight= fin.nextLine();
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
