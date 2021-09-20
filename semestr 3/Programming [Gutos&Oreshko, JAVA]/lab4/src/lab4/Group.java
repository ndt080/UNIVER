package lab4;

import java.util.ArrayList;


public class Group {
    private String number;
    private ArrayList<Student> students;

    public Group(String number) {
        this.number = number;
        this.students = new ArrayList<Student>();
    }

    public boolean addStudent(Student student) {
        return students.add(student);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void averageMark() {
        double mark = 0;
        int counter = 0;
        for (Student student : students) {
            for (Integer stm : student.getMarks()) {
                mark += stm;
                counter++;
            }
        }
        System.out.println("averageMark of all students: " + mark / counter);
    }

    public void eachStudentAverageMark() {
        for (Student student : students) {
            double mark = 0;
            int counter = 0;
            for (Integer stm : student.getMarks()) {
                mark += stm;
                counter++;
            }
            System.out.println("Name: " + student.getName() + "; AverageMark: " + mark / counter);
        }
    }

    //отличник - студент, у которого все отметки выше 4 по шкале от 0 до 5
    public void excellentStudent() {
        int amount = 0;
        for (Student student : students) {
            boolean isExcellent = true;
            for (Integer stm : student.getMarks()) {
                if (stm < 4) {
                    isExcellent = false;
                }
            }
            if (isExcellent) {
                amount++;
            }
        }
        System.out.println("amount of excellent students: " + amount);
    }

    public void has2() {
        int amount = 0;
        boolean has2 = false;
        for (Student student : students) {
            for (Integer stm : student.getMarks()) {
                if (stm == 2) {
                    has2 = true;
                }
            }
            if (has2) {
                amount++;
            }
        }
        System.out.println("amount of pure students: " + amount);
    }
}
