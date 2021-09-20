package lab4;

import java.util.ArrayList;


public class Student {

    private String name;
    private ArrayList<Integer> marks;

    public Student(String name) {
        this.name = name;
        this.marks = new ArrayList<Integer>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getMarks() {
        return marks;
    }

    public boolean addMark(int mark) {
        return marks.add(mark);
    }
}
