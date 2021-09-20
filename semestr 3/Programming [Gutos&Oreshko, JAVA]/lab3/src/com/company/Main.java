package com.company;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String fileName = "in.txt";
        Path path = Paths.get(fileName);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        try (Scanner reader = new Scanner(path)) {
            reader.useDelimiter(System.getProperty("line.separator"));
            while(reader.hasNext()){
                String expression = reader.next();
                try{
                    Object str = engine.eval(expression);
                    if(str.toString().equals("Infinity") || str.toString().equals("NaN")) {
                        System.out.println(">>> Oшибка: Деление на 0! <<<");
                    }else {
                        System.out.println(expression + "=" + str);
                    }
                } catch (Exception e){
                    System.out.println(">>> Ошибка: Некорректное выражение! <<<");
                }
            }
        } catch (IOException e){
            System.out.println(">>> Ошибка: Ошибка работы с файлом! <<<");
        }
    }
}