package Models;

import java.util.Objects;

public class Discipline {
    private String name;
    private int grade;

    public Discipline() {
    }

    public Discipline(String name) {
        this.name = name;
    }

    public boolean isPassed() {
        return grade >= 60;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object enteredObject) {
        if (this == enteredObject) return true;
        if (enteredObject == null || getClass() != enteredObject.getClass()) return false;
        Discipline that = (Discipline) enteredObject;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }

    @Override
    public String toString() {
        return String.format("Дисципліна:" + this.name + "| Оцінка: " + this.grade +
                "| Статус: " + (isPassed() ? "Складено" : "Не складено"));
    }
}