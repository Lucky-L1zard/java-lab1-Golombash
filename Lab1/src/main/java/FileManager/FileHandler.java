package FileManager;

import Models.Discipline;
import Models.School;
import Models.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FileHandler {

    public void exportToCSV(School school, String fileName) {
        // Сортування за прізвищем перед записом
        school.getListOfStudents().sort(Comparator.comparing(student->student.getSecondName()));

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(school.getName() + "\n");

            for (Student student : school.getListOfStudents()) {
                StringBuilder content = new StringBuilder();
                content.append(student.getFirstName()).append(",")
                        .append(student.getSecondName()).append(",");

                ArrayList<Discipline> disciplines = student.getLisOfDisciplines();
                for (int i = 0; i < disciplines.size(); i++) {
                    content.append(disciplines.get(i).getName()).append(":")
                            .append(disciplines.get(i).getGrade());
                    if (i < disciplines.size() - 1) content.append(";");
                }
                writer.write(content.toString() + "\n");
            }
            System.out.println("Дані школи '" + school.getName() + "' експортовано у " + fileName);
        } catch (IOException e) {
            System.out.println("Помилка запису: " + e.getMessage());
        }
    }

    public School importFromCSV(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Файл не знайдено!");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String schoolName = reader.readLine();
            if (schoolName == null) return null;

            School school = new School(schoolName);

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts.length < 2) continue;

                Student student = new Student(parts[0], parts[1]);

                if (parts.length > 2 && !parts[2].isEmpty()) {
                    String[] discParts = parts[2].split(";");
                    for (String d : discParts) {
                        String[] nameAndGrade = d.split(":");
                        if (nameAndGrade.length == 2) {
                            Discipline disc = new Discipline(nameAndGrade[0]);
                            disc.setGrade(Integer.parseInt(nameAndGrade[1]));
                            student.addDiscipline(disc);
                        }
                    }
                }
                school.addStudent(student);
            }
            return school;
        } catch (IOException e) {
            System.out.println("Помилка імпорту: " + e.getMessage());
            return null;
        }
    }
}
