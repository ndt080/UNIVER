package com.company;

public class Triangle {
    int a, b, c;

    Triangle(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int GetPerimeter() {
        return a + b + c;
    }

    public double GetArea() {
        double p = (a + b + c) / 2.0;
        return Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }

    public String GetType() {
        if (a == b && a == c)
            return "Equilateral";//Равносторонний
        else if (a == b || a == c || b == c)
            return "Isosceles";//Равнобедренный
        else
            return "Versatile";//Разносторонний
    }

    public String toString() {
        return String.format("a = %d b = %d c = %d", a, b, c);
    }
}
