package FileManager;

import Models.Discipline;
import Models.School;
import Models.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FileHandler {

    // Експорт у форматі CSV: Ім'я,Прізвище,Дисципліна1:Оцінка1;Дисципліна2:Оцінка2
    public void exportToCSV(School school, String fileName){
        // Сортування за прізвищем
        school.getListOfStudents().sort(Comparator.comparing(s->s.getSecondName()));
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Student student : school.getListOfStudents()) {
                StringBuilder content = new StringBuilder();
                content.append(student.getFirstName()).append(",")
                        .append(student.getSecondName()).append(",");

                ArrayList<Discipline> currentStudentDisciplines = student.getLisOfDisciplines();
                for (int i = 0; i < currentStudentDisciplines.size(); i++) {
                    content.append(currentStudentDisciplines.get(i).getName()).append(":")
                            .append(currentStudentDisciplines.get(i).getGrade());
                    if (i < currentStudentDisciplines.size() - 1) content.append(";");
                }

                writer.write(content.toString() + "\n");
            }
            System.out.println("Дані успішно експортовано у " + fileName);
        } catch (IOException e) {
            System.out.println("Сталася помилка при записі у файл: " + e.getMessage());
        }
    }

    // Імпорт з CSV
    public School importFromCSV(String fileName, String schoolName){
        School school = new School(schoolName);
        File file = new File(fileName);

        if (!file.exists()) try {
            throw new FileNotFoundException("Файл не знайдено!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts.length < 2) continue;

                Student student = new Student(parts[0], parts[1]);

                if (parts.length > 2) {
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
        }catch (IOException e) {
            System.out.println("Сталася помилка при імпорті з файлу: " + e.getMessage());
        }
        return school;
    }
}
