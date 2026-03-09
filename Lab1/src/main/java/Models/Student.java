package Models;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;

public class Student {
    private String firstName;
    private String secondName;
    private ArrayList<Discipline> disciplines = new ArrayList<>();

    public Student() {
    }

    public Student(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Student(String firstName, String secondName, ArrayList<Discipline> disciplines) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.disciplines = disciplines;
    }

    public void takeTest(String disciplineName, int score) {
        for (int i = 0; i < disciplines.size(); i++) {
            if (disciplines.get(i).getName().equals(disciplineName)) {
                Discipline choosenDiscipline = disciplines.get(i);
                choosenDiscipline.setGrade(choosenDiscipline.getGrade() + score);
                return;
            }
        }
        throw new RuntimeException("Not found discipline with Name " + disciplineName);
    }

    public double calculateAverageGrade() {
        if (disciplines.isEmpty()) return 0.0;
        double sum = 0.0;
        for (int i = 0; i < disciplines.size(); i++) {
            sum += disciplines.get(i).getGrade();
        }
        return sum / disciplines.size();
    }

    public void addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
    }

    public boolean DeleteDisciplineByName(String disciplineName) {
        return this.disciplines.removeIf(dis -> dis.getName().equals(disciplineName));
    }


    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFullName() {
        return firstName + " " + secondName;
    }

    public ArrayList<Discipline> getLisOfDisciplines() {
        return disciplines;
    }

    public void setLisOfDisciplines(ArrayList<Discipline> newDisciplines) {
        this.disciplines = newDisciplines;
    }

    @Override
    public boolean equals(Object eneteredObject) {
        if (this == eneteredObject) return true;
        if (eneteredObject == null || getClass() != eneteredObject.getClass()) return false;
        Student student = (Student) eneteredObject;
        return Objects.equals(this.firstName, student.firstName) &&
                Objects.equals(this.secondName, student.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getFullName());
    }

    @Override
    public String toString() {
        String result = "Учень: " + this.getFullName() + "\n" +
                "Середній бал: " + this.calculateAverageGrade() + "\n" +
                "Предмети:\n";
        for (int i = 0; i < disciplines.size(); i++) {
            result += "  - " + disciplines.get(i).toString() + "\n";
        }
        return result;
    }
}