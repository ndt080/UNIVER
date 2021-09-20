package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);
        int k = 0;
        double x = 0;

        System.out.print("Функция 'arcsin(x)'.\n Введите аргумент x: ");
        while(!reader.hasNextDouble()){
            System.out.println("ОШИБКА: Ошибка ввода!\n Введите аргумент x: ");
            reader.next();
        }
        x = reader.nextDouble();

        System.out.println("Введите число десятичных знаков после запятой: ");
        while(!reader.hasNextInt(10)){
            System.out.println("ОШИБКА: Ошибка ввода!\n Введите число десятичных знаков: ");
            reader.next();
        }
        k = reader.nextInt();

        double library_answer = Math.asin(x);
        double answer = taylor_row_function(x, k);
        System.out.printf("Ответ: %." + k + "f, Ответ из библиотеки: %." + k + "f\n", answer, library_answer);
    }
    private static double taylor_row_function(double x, int k){
        if(x >= -1 && x <= 1){
            double series_of_sum = x;
            double variable;
            int index = 3, beforeindex = 6, afterindex = 1;
            while(Math.abs(variable = afterindex * Math.pow(x, index) / beforeindex) > Math.pow(10, -k)){
                series_of_sum += variable;
                afterindex*=index;
                index+=2;
                beforeindex=index;
                for (int i=2; i<index;i+=2) {
                    beforeindex *= i;
                }
            }
            return series_of_sum;
        } else throw new ArithmeticException("ОШИБКА: Выход за область определения функции!");
    }

}