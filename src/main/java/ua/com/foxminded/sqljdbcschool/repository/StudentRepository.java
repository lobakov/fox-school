package ua.com.foxminded.sqljdbcschool.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.service.ConnectionService;

public class StudentRepository extends JdbcSchoolRepository<Student> {

    public StudentRepository(ConnectionService connectionService) {
        super(connectionService);
    }

    public void assignStudentToGroup(Long id, Long groupId) {
        String sqlStatement = "INSERT INTO students (group_id) VALUES (?) WHERE id = ?";
        try (Connection connection = connectionService.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, groupId);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public void assignCourseToStudent(Long id, Long courseId) {
        String sqlStatement = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
        try (Connection connection = connectionService.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);
            statement.setLong(2, courseId);
            statement.executeUpdate();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    @Override
    public void save(Student student) {
        String sqlStatement = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = connectionService.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    student.setId(keys.getLong(1));
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}
