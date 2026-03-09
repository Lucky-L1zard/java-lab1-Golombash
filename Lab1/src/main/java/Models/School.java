package Models;

import java.util.ArrayList;
import java.util.Objects;

public class
School {
    private String name;
    private ArrayList<Student> students = new ArrayList<>();

    public School() {
    }

    public School(String name) {
        this.name = name;
    }

    public double calculateAvarageSchoolRate() {
        if (students.isEmpty()) return 0.0;
        double sumOfAvarage = 0.0;
        for (int i = 0; i < students.size(); i++) {
            sumOfAvarage += students.get(i).calculateAverageGrade();
        }
        return sumOfAvarage / students.size();
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public boolean deleteStudentByNameAndSecondName(String studentName, String studentSecondName) {
        return students.removeIf(student -> student.getFirstName().equalsIgnoreCase(studentName)
                && student.getSecondName().equalsIgnoreCase(studentSecondName));
    }

    public String getName() {
        return name;
    }

    public ArrayList<Student> getListOfStudents() {
        return students;
    }

    @Override
    public boolean equals(Object enteredObject) {
        if (this == enteredObject) return true;
        if (enteredObject == null || getClass() != enteredObject.getClass()) return false;
        School school = (School) enteredObject;
        return Objects.equals(this.name, school.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        String result = "=== Школа: " + this.name + " ===\n" +
                "Рейтинг школи: " + this.calculateAvarageSchoolRate() + "\n" +
                "Список учнів:\n";
        for (int i = 0; i < students.size(); i++) {
            result += students.get(i).toString() + "---------------------------\n";
        }
        return result;
    }
}
