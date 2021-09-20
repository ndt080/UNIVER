package mypack;

import java.io.Serializable;
import java.util.Scanner;

public class Room implements Serializable
{
    String Number;
    String S;
    String Floor;
    String RooomsAmount;
    String Street;
    String Type;
    String Period;


    public static Room read( Scanner fin )
    {
        Room room = new Room();
        room.Number = fin.nextLine();
        if ( ! fin.hasNextLine())
            return null;
        room.S = fin.nextLine();
        if ( ! fin.hasNextLine())
            return null;
        room.Floor = fin.nextLine();
        if ( ! fin.hasNextLine())
            return null;
        room.RooomsAmount = fin.nextLine();
        if ( ! fin.hasNextLine())
            return null;
        room.Street = fin.nextLine();
        if ( ! fin.hasNextLine())
            return null;
        room.Type = fin.nextLine();
        if ( ! fin.hasNextLine())
            return null;
        room.Period = fin.nextLine();
        return room;
    }

    public Room() { }

    public String toString()
    {
        return new String ("Number: " + Number + ", S: " + S +
                ", Floor: " + Floor + ", Rooms: " + RooomsAmount +
                ", Adress: " + Street + ", Type: " + Type + ", Period:" + Period);
    }
}
