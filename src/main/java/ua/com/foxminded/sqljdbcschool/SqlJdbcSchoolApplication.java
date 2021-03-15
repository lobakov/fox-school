package ua.com.foxminded.sqljdbcschool;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import ua.com.foxminded.sqljdbcschool.config.ConnectionConfig;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.service.TablesCreationService;
import ua.com.foxminded.sqljdbcschool.service.TestDataGenerationService;
import ua.com.foxminded.sqljdbcschool.utility.FileReader;

/*
    b. Generate test data:

 * 10 groups with randomly generated names. The name should contain 2 characters, hyphen, 2 numbers

 * Create 10 courses (math, biology, etc)

 * 200 students. Take 20 first names and 20 last names and randomly combine them to generate students.

 * Randomly assign students to groups. Each group could contain from 10 to 30 students. It is possible that some groups will be without students or students without groups

 * Create relation MANY-TO-MANY between tables STUDENTS and COURSES. Randomly assign from 1 to 3 courses for each student

3. Write SQL Queries, it should be available from the application menu:

    a. Find all groups with less or equals student count

    b. Find all students related to course with given name

    c. Add new student

    d. Delete student by STUDENT_ID

    e. Add a student to the course (from a list)

    f. Remove the student from one of his or her courses*/

public class SqlJdbcSchoolApplication {

    public static void main(String[] args) {
        FileReader reader = new FileReader();
        ConnectionConfig config = new ConnectionConfig(reader);
        TablesCreationService tablesCreationService = new TablesCreationService(config);
        String dbFile = "db/migration/v1_init.sql";

        try {
            Class.forName(config.getDbDriver());
            tablesCreationService.createTablesFromFile(reader.read(dbFile));
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: Could not find driver " + config.getDbDriver());
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println("ERROR: Could not read migration script " + dbFile);
            e.printStackTrace();
        }

        TestDataGenerationService dataService = new TestDataGenerationService();
        Set<Group> groups = dataService.generateGroups();
        groups.forEach(System.out::println);

        Set<Student> students = dataService.generateStudents();
        students.forEach(System.out::println);

        dataService.assignStudentsToGroups(groups, students);
        System.out.println("===========================================================");
        groups.forEach(group -> {
            System.out.println("Group: " + group.getName());
            System.out.println("Number of students: " + group.getStudents().size());
            System.out.println("Students: " + group.getStudents());
        });
        List<Course> courses = dataService.assignCoursesToStudents(students);
        students.forEach(student -> {
            System.out.println("Student: " + student);
            System.out.println("Group: " + student.getGroup().getName());
            System.out.println("Courses: " + student.getCourses());
        });

        courses.forEach(course -> {
            System.out.println("Course: " + course);
            System.out.println("Course students: " + course.getStudents());
        });
//        System.out.println();

//        TablesPopulationService tablesPopulationService = new TablesPopulationService(new TestDataGenerationService());
//        tablesPopulationService.run();
//        SqlJdbcSchoolService schoolService = new SqlJdbcSchoolService();
//        schoolService.run();
    }
}
