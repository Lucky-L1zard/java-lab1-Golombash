import Models.Discipline;
import Models.School;
import Models.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelsTest {

    //Discipline tests
    @Test
    void testIsPassed_BoundaryValue_ReturnsTrue() {
        Discipline math = new Discipline("Math");
        math.setGrade(60);
        assertTrue(math.isPassed(), "60 балів достатньо для складання");
    }

    @Test
    void testIsPassed_BelowBoundary_ReturnsFalse() {
        Discipline math = new Discipline("Math");
        math.setGrade(59);
        assertFalse(math.isPassed(), " <60 балів не достатньо для складання");
    }

    //Student tests

    @Test
    void testCalculateAverageGrade_MultipleDisciplines_ReturnsCorrectAverage() {
        Student student = new Student("Ivan", "Ivanov");
        Discipline d1 = new Discipline("Math");
        d1.setGrade(80);
        Discipline d2 = new Discipline("Physics");
        d2.setGrade(100);
        student.addDiscipline(d1);
        student.addDiscipline(d2);

        assertEquals(90.0, student.calculateAverageGrade(), 0.01);
    }

    @Test
    void testCalculateAverageGrade_NoDisciplines_ReturnsZero() {
        Student student = new Student("Kurulo", "Rozumovskiy");
        assertEquals(0.0, student.calculateAverageGrade(), "Середній бал без предметів має бути 0");
    }

    @Test
    void testTakeTest_ValidDiscipline_UpdatesGrade() {
        Student student = new Student("Petro", "Petrenko");
        student.addDiscipline(new Discipline("History"));

        student.takeTest("History", 85);

        assertEquals(85, student.getLisOfDisciplines().get(0).getGrade());
    }

    @Test
    void testTakeTest_InvalidDiscipline_ThrowsException() {
        Student student = new Student("Petro", "Petrenko");
        assertThrows(RuntimeException.class, () -> {
            student.takeTest("Math", 100);
        }, "Має бути викинуто RuntimeException, якщо дисципліну не знайдено");
    }

    //School tests

    @Test
    void testCalculateAverageSchoolRate_TwoStudents_ReturnsCorrectAverage() {
        School school = new School("KPI Lyceum");

        Student s1 = new Student("S1", "Last1");
        Discipline d1 = new Discipline("IT");
        d1.setGrade(100);
        s1.addDiscipline(d1);

        Student s2 = new Student("S2", "Last2");
        Discipline d2 = new Discipline("IT");
        d2.setGrade(80);
        s2.addDiscipline(d2);

        school.addStudent(s1);
        school.addStudent(s2);

        assertEquals(90.0, school.calculateAvarageSchoolRate(), 0.01);
    }

    @Test
    void testCalculateAverageSchoolRate_MultipleSrudents_ReturnsCorrectAverage() {

        Student s1 = new Student("s1", "Second1");
        Student s2 = new Student("s2", "Second2");
        Student s3 = new Student("s3", "Second3");

        Discipline math = new Discipline("Math");
        Discipline phys0 = new Discipline("Physics");
        Discipline phys1 = new Discipline("Physics");
        Discipline phys2 = new Discipline("Physics");
        Discipline PE = new Discipline("Physical Education");
        Discipline bio = new Discipline("Biologic");

        s1.setLisOfDisciplines(new ArrayList<Discipline>(List.of(math, phys1)));
        s2.setLisOfDisciplines(new ArrayList<Discipline>(List.of(bio, phys2)));
        s3.setLisOfDisciplines(new ArrayList<Discipline>(List.of(PE, phys0)));

        s1.takeTest("Math", 71);
        s1.takeTest("Physics", 83);
        s2.takeTest("Physics", 99);
        s2.takeTest("Biologic", 100);
        s3.takeTest("Physics", 64);
        s3.takeTest("Physical Education", 74);

        School school = new School("Hogwards");
        school.addStudent(s1);
        school.addStudent(s2);
        school.addStudent(s3);
        assertEquals(81.83, school.calculateAvarageSchoolRate(), 0.01);
    }

    @Test
    void testSchoolEquals_SameName_ReturnsTrue() {
        School s1 = new School("School #1");
        School s2 = new School("School #1");
        assertEquals(s1, s2, "Школи з однаковою назвою мають бути еквівалентними");
    }

    @Test
    void testStudentHashCode_Consistency() {
        Student student = new Student("Oleg", "Vynnyk");
        int initialHash = student.hashCode();
        assertEquals(initialHash, student.hashCode(), "HashCode має бути стабільним");
    }
}
