package ua.com.foxminded.sqljdbcschool.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.connection.TestDatabaseConnection;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.service.ConnectionService;

public class CourseRepositoryTest {

    private static final int MAX_COURSES = 10;

    private static ConnectionService connectionService;
    private static Connection connection;
    private static CourseRepository courseRepository;

    @BeforeAll
    private static void setup() {
        connectionService = new TestDatabaseConnection();
        connection = connectionService.getConnection();
        courseRepository = new CourseRepository(connectionService);
    }

    @BeforeEach
    private void resetCoursesDb() {
        String dropStatement = "DROP TABLE IF EXISTS courses CASCADE";
        String createStatement = "CREATE TABLE courses (id SERIAL PRIMARY KEY, "
                + "name VARCHAR(255) NOT NULL UNIQUE, description TEXT)";

        try (Statement statement = connection.createStatement()){
            statement.execute(dropStatement);
            statement.execute(createStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSaveNewCourseWhenProvided() {
        String courseName = "Math";
        String courseDescription = "TestMath";
        Course expectedCourse = new Course(courseName, courseDescription);

        courseRepository.save(expectedCourse);
        Course actualCourse = getSavedCourse(expectedCourse.getName());

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void shouldFindAllCoursesWhenRequested() {
        List<Course> expectedCourses = setupCourses(MAX_COURSES);

        List<Course> actualCourses = courseRepository.findAll();

        assertThat(expectedCourses).hasSameElementsAs(actualCourses);
    }

    @Test
    public void shouldFindAllCoursesByStudentId() {
        List<Course> expectedCourses = setupCourses(MAX_COURSES / 2);

        Long id = prepareDbAndGetTestId(expectedCourses);
        List<Course> actualCourses = courseRepository.findAllCoursesByStudentId(id);

        assertThat(expectedCourses).hasSameElementsAs(actualCourses);
    }

    private Long prepareDbAndGetTestId(List<Course> expectedCourses) {
        createGroups();
        StudentRepository studentRepository = new StudentRepository(connectionService);
        Long id = createStudent(studentRepository);
        prepareStudentsCoursesDb(id, studentRepository, expectedCourses);
        return id;
    }

    private void createGroups() {
        String dropGroupsStatement = "DROP TABLE IF EXISTS groups CASCADE";
        String createGroupsStatement = "CREATE TABLE groups (id SERIAL PRIMARY KEY,"
                + " name CHAR(5) NOT NULL UNIQUE)";

        try (Statement groupsStatement = connection.createStatement()){
            groupsStatement.execute(dropGroupsStatement);
            groupsStatement.execute(createGroupsStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Long createStudent(StudentRepository studentRepository) {
        String dropStudentsStatement = "DROP TABLE IF EXISTS students CASCADE";
        String createStudentsStatement = "CREATE TABLE students (id SERIAL PRIMARY KEY,"
                + " group_id INT REFERENCES groups (id), first_name VARCHAR(255) NOT NULL,"
                + " last_name VARCHAR(255) NOT NULL)";

        try (Statement studentsStatement = connection.createStatement()){
            studentsStatement.execute(dropStudentsStatement);
            studentsStatement.execute(createStudentsStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Student student = studentRepository.save(new Student("TestName", "TestLastName"));
        return student.getId();
    }

    private void prepareStudentsCoursesDb(Long id, StudentRepository studentRepository,
                List<Course> expectedCourses) {
        String dropStudentsCoursesStatement = "DROP TABLE IF EXISTS students_courses CASCADE";
        String createStudentsCoursesStatement = "CREATE TABLE students_courses"
                + " (student_id INT NOT NULL REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,"
                + " course_id INT NOT NULL REFERENCES courses (id) ON UPDATE CASCADE,"
                + " PRIMARY KEY (student_id, course_id))";

        try (Statement studentsCoursesStatement = connection.createStatement()){
            studentsCoursesStatement.execute(dropStudentsCoursesStatement);
            studentsCoursesStatement.execute(createStudentsCoursesStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Course course: expectedCourses) {
            studentRepository.assignCourseToStudent(id, course.getId());
        }
    }

    private Course getSavedCourse(String courseName) {
        Course course = null;
        String sqlStatement = String.format("SELECT * FROM courses "
                + "WHERE name = \'%s\'", courseName);
        try (Connection connection = connectionService.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlStatement)) {
            while(resultSet.next()){
                Long id  = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                course = new Course(name, description);
                course.setId(id);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return course;
    }

    private List<Course> setupCourses(int maxCourses) {
        List<Course> courses = new ArrayList<>();
        for (int i = 1; i < maxCourses; i++) {
            String name = String.format("TestName%d", i);
            String description = String.format("TestDescription%d", 1);
            Course course = new Course(name, description);
            courses.add(course);
            courseRepository.save(course);
        }
        return courses;
    }
}
