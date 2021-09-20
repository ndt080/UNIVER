package com.company;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        File input_file = new File("input.txt");
        StringBuilder res = new StringBuilder();
        try(Scanner scanner = new Scanner(input_file)){
            while(scanner.hasNext()) {
                //System.out.print("Enter lengths: ");
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                int c = scanner.nextInt();
                Triangle triangle = new Triangle(a, b, c);
                String expected_res = scanner.next();

                res.append("===========================================================").append(System.lineSeparator());
                res.append(triangle).append(System.lineSeparator());
                res.append("Perimeter: ").append(triangle.GetPerimeter()).append(System.lineSeparator());
                res.append("Area: ").append(triangle.GetArea()).append(System.lineSeparator());
                res.append("Type: ").append(triangle.GetType()).append(System.lineSeparator());
                res.append("Expected Type: ").append(expected_res).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(res);

    }
}
