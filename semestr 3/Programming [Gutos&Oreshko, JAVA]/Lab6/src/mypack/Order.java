package mypack;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
    private double Square;
    private int Floors;

    public double GetSquare()
    {
        return this.Square;
    }
    public int GetFloors()
    {
        return this.Floors;
    }
    public void SetSquare(double square)
    {
        this.Square = square;
    }
    public void SetFloors(int floors)
    {
        this.Floors = floors;
    }


    public Order(double square, int floors){
        this.Square = square;
        this.Floors = floors;
    }

    @Override
    public String toString() {
        return "Order= Square: " + Square +" m^2, Floors: " + Floors +
                "";
    }
}

