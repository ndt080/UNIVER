package lab4;

import lab4.Group;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("Oleg");
        student1.addMark(2);
        student1.addMark(3);
        student1.addMark(4);
        student1.addMark(5);
        student1.addMark(4);
        student1.addMark(4);
        Student student2 = new Student("Sergey");
        student2.addMark(5);
        student2.addMark(5);
        student2.addMark(4);
        student2.addMark(5);
        student2.addMark(5);
        student2.addMark(5);
        student2.addMark(5);
        Student student3 = new Student("Maxim");
        student3.addMark(3);
        student3.addMark(3);
        student3.addMark(3);
        student3.addMark(3);
        student3.addMark(3);

        Group group = new Group("22023");
        group.addStudent(student1);
        group.addStudent(student2);
        group.addStudent(student3);
        group.averageMark();
        group.eachStudentAverageMark();
        group.excellentStudent();
        group.has2();
    }
}
