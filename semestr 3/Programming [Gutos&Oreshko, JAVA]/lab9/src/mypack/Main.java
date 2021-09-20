package mypack;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args)
    {
            SimpleGUI gui = new SimpleGUI();
            gui.go();
    }

    static final String filename = "Bills.dat";

    private static Scanner fin = new Scanner( System.in );

    static RepareBill read()
    {
        System.out.println("Write \"Exit\" to end data input");
        if ( fin.hasNextLine())
        {
            return RepareBill.read(fin);
        }
        return null;
    }

    static void deleteFile()
    {
        File f = new File( filename );
        f.delete();
    }

    static void appendFile() throws FileNotFoundException, IOException
    {
        RepareBill bill;
        System.out.println( "Enter Bill data: " );
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" ))
        {
            while (( bill = read())!= null )
            {
                Buffer.writeObject( raf, bill );
            }
        }
    }

    static void printInformation(String command, String SortIndexType, String key) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw"))
        {
            ArrayList array = new ArrayList<RepareBill>();
            long pos;
            while ((pos = raf.getFilePointer()) < raf.length())
            {
                RepareBill bill = (RepareBill) Buffer.readObject(raf, pos);
                array.add(bill);

            }
            if (command.equals("Default"))
            {
                printBills(array);
                return;
            }

            // print sorted
            Comparator<RepareBill> comp = null;
            RepareBill KeyElement = null;
            boolean KeyFound = false;
            if (SortIndexType.equals("firm")) {
                comp = (o1, o2) -> o1.firm.compareTo(o2.firm);
                for (ListIterator<RepareBill> iter = array.listIterator(); iter.hasNext(); )
                {
                    RepareBill element = iter.next();
                    if (element.firm.compareTo(key) < 0) {
                        KeyElement = element;
                    } else if (element.firm.compareTo(key) == 0) {
                        KeyElement = element;
                        KeyFound = true;
                        break;
                    }
                }
            } else if (SortIndexType.equals("jobKind"))
            {
                comp = (o1, o2) -> o1.jobKind.compareTo(o2.jobKind);
                for (ListIterator<RepareBill> iter = array.listIterator(); iter.hasNext(); ) {
                    RepareBill element = iter.next();
                    if (element.jobKind.compareTo(key) == -1) {
                        KeyElement = element;
                    } else if (element.jobKind.compareTo(key) == 0) {
                        KeyElement = element;
                        KeyFound = true;
                        break;
                    }
                }

            } else if (SortIndexType.equals("date"))
            {
                comp = (o1, o2) -> o1.date.compareTo(o2.date);
                for (ListIterator<RepareBill> iter = array.listIterator(); iter.hasNext(); )
                {
                    RepareBill element = iter.next();
                    String[] date = key.split(" ");
                    if (element.date.compareTo(new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]))) == -1) {
                        KeyElement = element;
                    } else if (element.date.compareTo(new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]))) == 0) {
                        KeyElement = element;
                        KeyFound = true;
                        break;
                    }
                }
            }
            array.sort(comp);
            if (command.equals("Print Sorted")) {
                printBills(array);
                return;
            }
            if (command.equals("Print Sorted R")) {
                Collections.reverse(array);
                printBills(array);
                return;
            }

            // Find equal|less|grater than key
            if (command.equals("Find Key")) {
                if (KeyFound)
                    System.out.println(KeyElement.toString());
                else System.out.println("No such record!");
            }
            if (command.equals("Print less than key")) {
                ArrayList less_records = new ArrayList<RepareBill>();
                for (var bill : array) {
                    less_records.add(bill);
                    if (bill == KeyElement)
                        break;
                }
                printBills(less_records);
            }
            if (command.equals("Print greater than key")) {
                if (KeyElement == null) {
                    printBills(array);
                    return;
                }
                ArrayList greater_records = new ArrayList<RepareBill>();
                Collections.reverse(array);
                for (var bill : array) {
                    greater_records.add(bill);
                    if (bill == KeyElement) {
                        greater_records.remove(KeyElement);
                        break;
                    }
                }
                printBills(greater_records);
                return;
            }
            if (command.equals("Remove By Index")) {
                if(KeyFound){
                    remove(array, KeyElement);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    static void printBills(ArrayList<RepareBill> array){
        int i = 0;
        for(var bill: array) {
            i++;
            System.out.println(i + ": " + bill.toString());
        }
    }
    static void remove(ArrayList<RepareBill> array, RepareBill key) throws IOException, ParseException {
        array.remove(key);
        File f = new File( filename );
        f.delete();
        saveAfterRemove(array);

    }

    static void saveAfterRemove(ArrayList<RepareBill> array) throws FileNotFoundException, IOException, ParseException{
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
            for(int i = 0; i < array.size(); i++){
                Buffer.writeObject( raf, array.get(i) );
            }
        }
    }

}
