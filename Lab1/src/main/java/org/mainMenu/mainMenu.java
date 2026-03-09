package org.mainMenu;

import Models.Discipline;
import Models.School;
import Models.Student;
import FileManager.FileHandler;

import java.beans.Encoder;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class mainMenu {
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            Scanner inputScanner = new Scanner(System.in);
            FileHandler fileHandler = new FileHandler();

            System.out.println("(можна залишити пустими, якщо в подальшому плануєте імпортувати певні данні)" +
                    "\n Введіть назву вашої школи :");
            String schoolName = inputScanner.nextLine();
            School school = new School(schoolName);
            boolean running = true;

            while (running) {

                System.out.println("Натисніть \"Enter\" для продовження");
                inputScanner.nextLine();
                printMenu();
                System.out.print("Оберіть пункт меню: ");
                String choice = inputScanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Ім'я школяра: ");
                        String firstName = inputScanner.nextLine();
                        System.out.print("Прізвище школяра: ");
                        String lastName = inputScanner.nextLine();
                        school.addStudent(new Student(firstName, lastName));
                        System.out.println("Школяра додано успішно.");
                        break;

                    case "2":
                        System.out.print("Ім'я школяра: ");
                        String sFirstName = inputScanner.nextLine();
                        System.out.print("Прізвище школяра: ");
                        String sLastName = inputScanner.nextLine();
                        if (school.deleteStudentByNameAndSecondName(sFirstName, sLastName)) {
                            System.out.println("Школяра видалено успішно.");
                        } else {
                            System.out.println("Школяра з введеними данними не знайдено");
                        }
                        break;

                    case "3":
                        Student sForDisc = findStudentFlow(school, inputScanner);
                        if (sForDisc != null) {
                            System.out.print("Назва нової дисципліни: ");
                            String discName = inputScanner.nextLine();
                            sForDisc.addDiscipline(new Discipline(discName));
                            System.out.println("Дисципліну додано.");
                        }
                        break;
                    case "4":
                        Student sForDelDisc = findStudentFlow(school, inputScanner);
                        if (sForDelDisc != null) {
                            System.out.print("Назва дисципліни для видалення: ");
                            String discNameToDel = inputScanner.nextLine();
                            if(!sForDelDisc.DeleteDisciplineByName(discNameToDel))
                            {
                                System.out.print("Дисципліну з такою назвою у цього учня не знайждено!");
                            }else {
                                System.out.println("Дисципліну додано.");
                            }

                        }
                        break;

                    case "5":
                        Student sForTest = findStudentFlow(school, inputScanner);
                        if (sForTest != null) {
                            System.out.print("Назва дисципліни для тесту: ");
                            String dName = inputScanner.nextLine();
                            System.out.print("Оцінка (0-100): ");
                            try {
                                int grade = Integer.parseInt(inputScanner.nextLine());
                                sForTest.takeTest(dName, grade);
                                System.out.println("Оцінку успішно виставлено.");
                            } catch (NumberFormatException e) {
                                System.out.println("Помилка: Введіть числове значення оцінки.");
                            } catch (RuntimeException e) {
                                System.out.println("Помилка: " + e.getMessage());
                            }
                        }
                        break;

                    case "6":
                        System.out.println("\n--- ПОТОЧНИЙ СТАН ШКОЛИ ---");
                        System.out.println(school.toString());
                        break;

                    case "7":
                        fileHandler.exportToCSV(school, "school_data.csv");
                        break;

                    case "8":
                        School imported = fileHandler.importFromCSV("school_data.csv");
                        if (imported != null) {
                            school = imported;
                            System.out.println("Дані успішно завантажено з файлу.");
                        }
                        break;

                    case "0":
                        running = false;
                        System.out.println("Завершення роботи.");
                        break;

                    default:
                        System.out.println("Некоректний вибір. Спробуйте ще раз.");
                }
            }
            inputScanner.close();
        } catch (Exception e) {
            System.out.println("Щось пішло не так :" + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n===========================");
        System.out.println("   Меню");
        System.out.println("===========================");
        System.out.println("1. Додати школяра");
        System.out.println("2. Видалити школяра");
        System.out.println("3. Додати дисципліну школяру");
        System.out.println("4. Видалиити дисципліну школяру");
        System.out.println("5. Виставити оцінку (Тест)");
        System.out.println("6. Показати всіх школярів та бали");
        System.out.println("7. Зберегти дані (Експорт + Сортування)");
        System.out.println("8. Завантажити дані (Імпорт)");
        System.out.println("0. Вихід");
    }

    private static Student findStudentFlow(School school, Scanner sc) {
        System.out.print("Введіть прізвище школяра для пошуку: ");
        String lastName = sc.nextLine();
        for (Student s : school.getListOfStudents()) {
            if (s.getSecondName().equalsIgnoreCase(lastName)) {
                return s;
            }
        }
        System.out.println("Школяра з таким прізвищем не знайдено.");
        return null;
    }
}