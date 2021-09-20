package mypack;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.spi.CalendarDataProvider;

public class RepareBill implements Serializable {
    String firm;
    String jobKind;
    String unit;
    double unitCost;
    double volume;
    GregorianCalendar date;
    //public void setSortIndex(String SortIndex){this.sortIndex = SortIndex;}
    public static RepareBill read( Scanner fin ) {
        RepareBill bill = new RepareBill();
        bill.firm = fin.nextLine();
        if (bill.firm.equals("Exit"))
            return null;
        if ( ! fin.hasNextLine()) return null;
        bill.jobKind = fin.nextLine();
        if ( ! fin.hasNextLine()) return null;
        bill.unit = fin.nextLine();
        if ( ! fin.hasNextLine()) return null;
        bill.unitCost = Double.parseDouble(fin.nextLine());
        if ( ! fin.hasNextLine()) return null;
        bill.volume = Double.parseDouble(fin.nextLine());
        if ( ! fin.hasNextLine()) return null;
        String[] date = fin.nextLine().split(" ");
        if(date.length < 3) return null;
        bill.date = new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        return bill;
    }

    RepareBill(){}

    public String toString() {
        return new String (
                "Компания: " + firm + "|" +
                        "Вид работ: " + jobKind + "|" +
                        "Удельная единица: " + unit + "|" +
                        "Стоимость удельной единицы: " + unitCost + "|" +
                        "Объём работы(в уд. ед.): " + volume + "|"
                        + "Дата выполнения работ: " + date.get(Calendar.DAY_OF_MONTH) +"." + date.get(Calendar.MONTH) + "." + date.get(Calendar.YEAR)
        );
    }

}
