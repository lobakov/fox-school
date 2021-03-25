package ua.com.foxminded.sqljdbcschool.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.service.ConnectionService;

public class StudentRepository extends JdbcSchoolRepository<Student> {

    public StudentRepository(ConnectionService connectionService) {
        super(connectionService);
    }

    public void assignStudentToGroup(Long id, Long groupId) throws InterruptedException {
        String sqlStatement = "UPDATE students SET group_id = ? WHERE id = ?";
        try (Connection connection = connectionService.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, groupId);
            statement.setLong(2, id);
            System.out.println(statement.toString());
            statement.executeUpdate();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public List<Student> findAllStudentsByCourseName(String courseName, GroupRepository groupRepository) {
        List<Student> result = new ArrayList<>();
        String query = "SELECT students.id, first_name, last_name, group_id FROM students JOIN "
                + "(SELECT student_id FROM students_courses AS stc JOIN "
                + "(SELECT id FROM courses WHERE name = '?') AS ids "
                + "ON stc.course_id = ids.id) AS sti ON students.id = sti.student_id";

        try (Connection connection = connectionService.getConnection();
                    PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courseName);
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while(resultSet.next()){
                    Long id  = resultSet.getLong("id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    Student student = new Student(firstName, lastName);
                    student.setId(id);
                    Long groupId = resultSet.getLong("group_id");
                    student.setGroupId(groupId);
                    Group group = groupRepository.findGroupById(groupId);
                    student.setGroup(group);
                    result.add(student);
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return result;
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

    public void deleteStudentById(Long id) {
        String sqlStatement = "DELETE FROM students WHERE id = ?";
        try (Connection connection = connectionService.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public void removeStudentFromCourses(Long studentId, List<Long> courseIds) {
        String sqlBase = "DELETE FROM students_courses WHERE student_id = ? AND course_id IN (";
        StringJoiner sqlStatement = new StringJoiner("");

        sqlStatement.add(sqlBase);
        int numberOfIds = courseIds.size();
        for (int i = 1; i <= numberOfIds; i++) {
            sqlStatement.add("?");
            if (i < numberOfIds) {
                sqlStatement.add(", ");
            }
        }
        sqlStatement.add(")");

        try (Connection connection = connectionService.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement.toString())) {
            statement.setLong(0, studentId);
            for (int i = 1; i < numberOfIds; i++) {
                statement.setLong(i , courseIds.get(i));
            }
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
